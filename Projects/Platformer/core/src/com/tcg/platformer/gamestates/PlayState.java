package com.tcg.platformer.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.platformer.MyHelpers;
import com.tcg.platformer.Platformer;
import com.tcg.platformer.entities.*;
import com.tcg.platformer.graphics.HUD;
import com.tcg.platformer.managers.ContentManager;
import com.tcg.platformer.managers.GameStateManager;

import java.util.*;

import static com.tcg.platformer.GameData.*;

public class PlayState extends AbstractGameState {

    private World world;
    private float accumulator;
    private Viewport b2dView;
    private Box2DDebugRenderer b2dRenderer;
    private Set<AbstractB2DSpriteEntity> toRemove;
    private Stack<Vector2> particlesToAdd;

    private Level map;
    private Viewport gameView;
    private Viewport parallaxView;

    private Player player;

    private SmoothEntityFollow camFollow;

    private TilingBackground background;

    private int coins;
    private HUD hud;

    private List<Particle> particles;

    private int currentLevel;
    private boolean shouldGoToNextLevel;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        initPhys();
        currentLevel = 0;
        toRemove = new TreeSet<AbstractB2DSpriteEntity>();
        map = new Level(currentLevel, world);
        gameView = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        gameView.getCamera().position.set(WORLD_WIDTH * 0.5f, WORLD_HEIGHT * 0.5f, 0);
        gameView.getCamera().update();
        parallaxView = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);

        player = new Player(world, map.getPlayerSpawnPosition());
        player.setShootEvent(new EntityEvent<Player>() {
            @Override
            public void accept(Player entity) {
                shootLaser(entity);
            }
        });
        camFollow = new SmoothEntityFollow(25f, map.getPlayerSpawnPosition(), true);
        camFollow.setToFollow(player);

        background = new TilingBackground(ContentManager.Image.LEVEL_BG, map.getTopRight().x, map.getTopRight().y);

        coins = 0;

        hud = new HUD();

        particles = new ArrayList<Particle>();
        particlesToAdd = new Stack<Vector2>();
        shouldGoToNextLevel = false;

    }

    private void shootLaser(Player entity) {
        Platformer.content.playSound(ContentManager.SoundEffect.LASER);
        map.addObject(new Laser(world, entity));
    }

    private void goToNextLevel() {
        shouldGoToNextLevel = false;
        currentLevel++;
        map.removeAllObjects();
        removeWorldBodies();
        if(map.loadLevel(currentLevel, world)) {
            player.dispose();
            player = new Player(world, map.getPlayerSpawnPosition());
            player.setShootEvent(new EntityEvent<Player>() {
                @Override
                public void accept(Player entity) {
                    shootLaser(entity);
                }
            });
            background.setWidth(map.getTopRight().x);
            background.setHeight(map.getTopRight().y);
            coins = 0;
            camFollow.setToFollow(player);
            camFollow.setPos(map.getPlayerSpawnPosition());
            toRemove.clear();
        } else {
            switchState(GameStateType.TITLE);
        }
    }

    private void removeWorldBodies() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            world.destroyBody(body);
        }
    }

    private void initPhys() {
        accumulator = 0;
        world = new World(new Vector2(0, GRAVITY), true);
        world.setContactListener(new GameContactListener());
        b2dRenderer = new Box2DDebugRenderer();
        b2dView = new FitViewport(WORLD_WIDTH * METERS_PER_PIXEL, WORLD_HEIGHT * METERS_PER_PIXEL);
    }

    @Override
    public void handleInput(float dt) {
        player.handleInput();
    }

    @Override
    public void update(float dt) {
        removeBodies();
        physicsStep(dt);
        player.update(dt);
        map.updateObjects(dt);
        updateView();
        hud.update(dt, coins);
        updateParticles(dt);
        addParticles();
        if(shouldGoToNextLevel) {
            goToNextLevel();
        }
    }

    private void addParticles() {
        while(!particlesToAdd.empty()) {
            Vector2 pos = particlesToAdd.pop();
            particles.add(new Particle(world, pos));
        }
    }

    private void updateParticles(float dt) {
        Iterator<Particle> iter = particles.iterator();
        while (iter.hasNext()) {
            Particle p = iter.next();
            p.update(dt);
            if (p.getY() < 0) {
                toRemove.add(p);
                iter.remove();
            }
        }
    }

    private void removeBodies() {
        Iterator<AbstractB2DSpriteEntity> iterator = toRemove.iterator();
        while (iterator.hasNext()) {
            AbstractB2DSpriteEntity entity = iterator.next();
            world.destroyBody(entity.getBody());
            map.remove(entity);
            iterator.remove();
        }
    }

    private void updateView() {
        camFollow.update();
        gameView.getCamera().position.set(camFollow.getPos(), 0);
        MyHelpers.clampCamera(gameView, Vector2.Zero, map.getTopRight());
        parallaxView.getCamera().position.set(new Vector3(gameView.getCamera().position).scl(0.75f));
        MyHelpers.clampCamera(parallaxView, Vector2.Zero, map.getTopRight());
        gameView.apply();
        parallaxView.apply();
        b2dView.getCamera().position.set(gameView.getCamera().position.x * METERS_PER_PIXEL, gameView.getCamera().position.y * METERS_PER_PIXEL, 0);
        b2dView.apply();
    }

    private void physicsStep(float dt) {
        accumulator += Math.min(dt, 0.25f);
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(parallaxView.getCamera().combined);
        background.draw(sb);
        sb.end();
        map.render(gameView);
        sb.begin();
        sb.setProjectionMatrix(gameView.getCamera().combined);
        player.draw(dt, sb, sr);
        map.renderObjects(dt, sb, sr);
        drawParticles(dt, sb, sr);
        sb.end();
        hud.draw(dt, sb, sr);
        if (DEBUG) {
            b2dRenderer.render(world, b2dView.getCamera().combined);
        }
    }

    private void drawParticles(float dt, SpriteBatch sb, ShapeRenderer sr) {
        for (Particle particle : particles) {
            particle.draw(dt, sb, sr);
        }
    }

    @Override
    public void resize(int width, int height) {
        b2dView.update(width, height);
        gameView.update(width, height);
        parallaxView.update(width, height);
        hud.resize(width, height);
    }

    @Override
    public void dispose() {
        removeWorldBodies();
        map.removeAllObjects();
        toRemove.clear();
        world.dispose();
        player.dispose();
    }

    public void spawnParticles(int amount, Vector2 position) {
        for (int i = 0; i < amount; i++) {
            particlesToAdd.push(position);
        }
    }

    class GameContactListener implements ContactListener {

        private int playerFootContacts = 0;

        private boolean isUserData(Fixture fixture, B2DUserData data) {
            return (fixture.getUserData() != null && fixture.getUserData().equals(data));
        }

        @Override
        public void beginContact(Contact contact) {
            checkPlayerGround(contact);
            checkCoin(contact);
            checkLaser(contact);
            checkLevelEnd(contact);
        }

        private void checkLevelEnd(Contact contact) {
            if(isUserData(contact.getFixtureA(), B2DUserData.LEVEL_END) || isUserData(contact.getFixtureB(), B2DUserData.LEVEL_END)) {
                shouldGoToNextLevel = true;
            }
        }

        private void checkLaser(Contact contact) {
            if (isUserData(contact.getFixtureA(), B2DUserData.LASER)) {
                removeBody(contact.getFixtureA().getBody());
                if (isUserData(contact.getFixtureB(), B2DUserData.FLY)) {
                    laserHitFly(contact.getFixtureB().getBody());
                }
            }
            if (isUserData(contact.getFixtureB(), B2DUserData.LASER)) {
                removeBody(contact.getFixtureB().getBody());
                if (isUserData(contact.getFixtureA(), B2DUserData.FLY)) {
                    laserHitFly(contact.getFixtureA().getBody());
                }
            }
        }

        private void checkCoin(Contact contact) {
            if (isUserData(contact.getFixtureA(), B2DUserData.COIN)) {
                getCoin(contact.getFixtureA().getBody());
            }
            if (isUserData(contact.getFixtureB(), B2DUserData.COIN)) {
                getCoin(contact.getFixtureB().getBody());
            }
        }

        private void checkPlayerGround(Contact contact) {
            if (isUserData(contact.getFixtureA(), B2DUserData.PLAYER_FOOT) || isUserData(contact.getFixtureB(), B2DUserData.PLAYER_FOOT)) {
                playerFootContacts++;
            }
            player.setOnGround(playerFootContacts > 0);
        }

        private void removeBody(Body laser) {
            toRemove.add((AbstractB2DSpriteEntity) laser.getUserData());
        }


        private void getCoin(Body coin) {
            removeBody(coin);
            Platformer.content.playSound(ContentManager.SoundEffect.COIN);
            coins++;
        }

        private void laserHitFly(Body fly) {
            removeBody(fly);
            spawnParticles(20, fly.getPosition());
            Platformer.content.playSound(ContentManager.SoundEffect.FLY_DEATH);
        }

        @Override
        public void endContact(Contact contact) {
            if (isUserData(contact.getFixtureA(), B2DUserData.PLAYER_FOOT) || isUserData(contact.getFixtureB(), B2DUserData.PLAYER_FOOT)) {
                playerFootContacts--;
            }
            player.setOnGround(playerFootContacts > 0);
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {
        }
    }

}
