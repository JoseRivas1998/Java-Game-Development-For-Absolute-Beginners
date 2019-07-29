package com.tcg.asteroids.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.asteroids.Asteroids;
import com.tcg.asteroids.MyHelpers;
import com.tcg.asteroids.entities.*;
import com.tcg.asteroids.graphics.HUD;
import com.tcg.asteroids.managers.ContentManager;
import com.tcg.asteroids.managers.GameStateManager;
import com.tcg.asteroids.managers.input.MyInput;
import com.tcg.asteroids.util.Timer;
import com.tcg.asteroids.util.TimerEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends AbstractGameState {

    private static final int INITIAL_ASTEROIDS = 6;
    private static final float MIN_ASTEROID_SPAWN_TIME = 1f;
    private static final float MAX_ASTEROID_SPAWN_TIME = 4f;
    public static final int INITIAL_LIVES = 3;
    public static final int INITIAL_SCORE = 0;
    public static final int SCORE_TO_WIN = 600;
    public static final int SCORE_PER_ASTEROID = 10;
    public static final int SCORE_PER_ENEMY = 50;

    public static final float SHIP_RESPAWN_TIME = 1f;

    private static final int NUM_STARS = 100;
    private static final int ASTEROID_EXPLODE_PARTICLES = 15;
    public static final int SHIP_EXPLODE_PARTICLES = 25;
    private static final float PULSE_TIME = 0.75f;

    public static final float ENEMY_MIN_SPAWN_TIME = 1.0f;
    public static final float ENEMY_MAX_SPAWN_TIME = 5.0f;
    public static final float ENEMY_MIN_SHOOT_TIME = 1.0f;
    public static final float ENEMY_MAX_SHOOT_TIME = 2.0f;

    private Viewport viewport;
    private Ship ship;
    private boolean shipAlive;
    private Timer shipRespawnTimer;

    private List<Asteroid> asteroids;
    private Timer asteroidSpawner;

    private List<Star> stars;

    private ContentManager.SoundEffect pulse;
    private Timer pulseTimer;

    private List<Bullet> bullets;

    private List<Particle> particles;

    private int score;
    private int lives;
    private HUD hud;

    private EnemyContainer enemyContainer;
    private Timer enemySpawner;
    private Timer enemyShooter;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        viewport = new FitViewport(Asteroids.WORLD_WIDTH, Asteroids.WORLD_HEIGHT);
        ship = new Ship();
        shipRespawnTimer = new Timer(SHIP_RESPAWN_TIME, Timer.TimerType.RUN_ONCE);
        shipRespawnTimer.setTimerEvent(new TimerEvent() {
            @Override
            public void accept(Timer timer) {
                if (lives <= 0) {
                    switchState(GameStateType.GAME_OVER);
                } else {
                    reset();
                }
            }
        });

        asteroids = new ArrayList<Asteroid>();
        asteroidSpawner = new Timer(MathUtils.random(MIN_ASTEROID_SPAWN_TIME, MAX_ASTEROID_SPAWN_TIME), Timer.TimerType.RUN_CONTINUOUS);
        asteroidSpawner.setTimerEvent(new TimerEvent() {
            @Override
            public void accept(Timer timer) {
                timer.setTime(MathUtils.random(MIN_ASTEROID_SPAWN_TIME, MAX_ASTEROID_SPAWN_TIME));
                float x;
                float y;
                if (MathUtils.randomBoolean()) {
                    //Spawn asteroid on vertical edge
                    x = MyHelpers.choose(0, Asteroids.WORLD_WIDTH);
                    y = MathUtils.random(0, Asteroids.WORLD_HEIGHT);
                } else {
                    x = MathUtils.random(0, Asteroids.WORLD_WIDTH);
                    y = MyHelpers.choose(0, Asteroids.WORLD_HEIGHT);
                }
                asteroids.add(new Asteroid(x, y));
            }
        });

        bullets = new ArrayList<Bullet>();

        stars = new ArrayList<Star>();
        for (int i = 0; i < NUM_STARS; i++) {
            stars.add(new Star());
        }

        pulseTimer = new Timer(PULSE_TIME, Timer.TimerType.RUN_CONTINUOUS);
        pulse = MyHelpers.choose(ContentManager.SoundEffect.PULSE_HIGH, ContentManager.SoundEffect.PULSE_LOW);
        pulseTimer.setTimerEvent(new TimerEvent() {
            @Override
            public void accept(Timer timer) {
                Asteroids.content.playSound(pulse);
                pulse = pulse.equals(ContentManager.SoundEffect.PULSE_LOW) ? ContentManager.SoundEffect.PULSE_HIGH : ContentManager.SoundEffect.PULSE_LOW;
            }
        });

        particles = new ArrayList<Particle>();

        hud = new HUD();

        score = INITIAL_SCORE;
        lives = INITIAL_LIVES;

        enemyContainer = new EnemyContainer();
        enemySpawner = new Timer(MathUtils.random(ENEMY_MIN_SPAWN_TIME, ENEMY_MAX_SPAWN_TIME), Timer.TimerType.RUN_CONTINUOUS);
        enemySpawner.setTimerEvent(new TimerEvent() {
            @Override
            public void accept(Timer timer) {
                timer.setTime(MathUtils.random(ENEMY_MIN_SPAWN_TIME, ENEMY_MAX_SPAWN_TIME));
                enemyContainer.activateEnemy();
            }
        });

        enemyShooter = new Timer(MathUtils.random(ENEMY_MIN_SHOOT_TIME, ENEMY_MAX_SHOOT_TIME), Timer.TimerType.RUN_CONTINUOUS);
        enemyShooter.setTimerEvent(new TimerEvent() {
            @Override
            public void accept(Timer timer) {
                timer.setTime(MathUtils.random(ENEMY_MIN_SHOOT_TIME, ENEMY_MAX_SHOOT_TIME));
                if(enemyContainer.isEnemyActive()) {
                    Asteroids.content.playSound(ContentManager.SoundEffect.SAUCER_SHOOT);
                    Vector2 enemyPos = enemyContainer.getEnemyPosition();
                    float angle = 0.0f;
                    switch(enemyContainer.getEnemyType()) {
                        case SMALL:
                            angle = MathUtils.random(MathUtils.PI2);
                            break;
                        case LARGE:
                            float dX = ship.getX() - enemyPos.x;
                            float dY = ship.getY() - enemyPos.y;
                            angle = MathUtils.atan2(dY, dX);
                            break;
                    }
                    bullets.add(new Bullet(enemyPos, angle, Bullet.BulletType.ENEMY));
                }
            }
        });

        reset();
    }

    @Override
    public void handleInput(float dt) {
        if (shipAlive) {
            ship.handleInput(dt);
        }
        if (MyInput.keyCheckPressed(MyInput.SHOOT)) {
            Asteroids.content.playSound(ContentManager.SoundEffect.SHOOT);
            bullets.add(new Bullet(ship));
        }
    }

    @Override
    public void update(float dt) {
        viewport.apply(true);
        if (shipAlive) {
            ship.update(dt);
        } else {
            shipRespawnTimer.update(dt);
        }
        if (enemyContainer.isEnemyActive()) {
            updateEnemy(dt);
        } else {
            enemySpawner.update(dt);
        }
        asteroidSpawner.update(dt);
        pulseTimer.update(dt);
        updateAsteroids(dt);
        updateBullets(dt);
        updateParticles(dt);
        hud.update(score, lives, shipAlive ? ship.getSpeed() : 0f);
        if (shipAlive && score >= SCORE_TO_WIN) {
            switchState(GameStateType.WIN);
        }
    }

    private void updateEnemy(float dt) {
        enemyContainer.updateEnemy(dt);
        if (shipAlive && enemyContainer.enemyCollidingWith(ship)) {
            destroyShip();
            destroyEnemy();
            return;
        }
        enemyShooter.update(dt);
        float enemyX = enemyContainer.getEnemyX();
        if (enemyX < 0 || enemyX > Asteroids.WORLD_WIDTH) {
            enemyContainer.deactivateEnemy();
            Asteroids.content.stopSound(ContentManager.SoundEffect.LARGE_ENEMY);
            Asteroids.content.stopSound(ContentManager.SoundEffect.SMALL_ENEMY);
            enemyShooter.reset();
        }
    }

    private void destroyEnemy() {
        Enemy enemy = enemyContainer.deactivateEnemy();
        addParticles(enemy, SHIP_EXPLODE_PARTICLES);
        Asteroids.content.playSound(ContentManager.SoundEffect.EXPLODE);
        enemySpawner.reset();
        enemyShooter.reset();
        Asteroids.content.stopSound(ContentManager.SoundEffect.LARGE_ENEMY);
        Asteroids.content.stopSound(ContentManager.SoundEffect.SMALL_ENEMY);
        score += SCORE_PER_ENEMY;
    }

    private void updateAsteroids(float dt) {
        for (Asteroid asteroid : asteroids) {
            asteroid.update(dt);
            if (asteroid.collidingWith(ship) && shipAlive) {
                destroyShip();
            }
        }
    }

    private void updateBullets(float dt) {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        List<Asteroid> asteroidsToAdd = new ArrayList<Asteroid>();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update(dt);
            if (bullet.getX() <= 0 ||
                    bullet.getX() >= Asteroids.WORLD_WIDTH ||
                    bullet.getY() <= 0 ||
                    bullet.getY() >= Asteroids.WORLD_HEIGHT) {
                bulletIterator.remove();
                continue;
            }
            if (ship.collidingWith(bullet) && bullet.isType(Bullet.BulletType.ENEMY) && shipAlive) {
                destroyShip();
                bulletIterator.remove();
                continue;
            }
            if (enemyContainer.isEnemyActive()) {
                if(enemyContainer.enemyCollidingWith(bullet) && bullet.isType(Bullet.BulletType.PLAYER)) {
                    destroyEnemy();
                    bulletIterator.remove();
                    continue;
                }
            }
            Iterator<Asteroid> asteroidIterator = asteroids.iterator();
            boolean asteroidFound = false;
            while (asteroidIterator.hasNext() && !asteroidFound) {
                Asteroid asteroid = asteroidIterator.next();
                if (bullet.collidingWith(asteroid)) {
                    switch (asteroid.getAsteroidType()) {
                        case LARGE:
                            asteroidsToAdd.add(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.MEDIUM));
                            asteroidsToAdd.add(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.MEDIUM));
                            break;
                        case MEDIUM:
                            asteroidsToAdd.add(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.SMALL));
                            asteroidsToAdd.add(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.SMALL));
                            break;
                        case SMALL:
                            break;
                    }
                    if (bullet.isType(Bullet.BulletType.PLAYER)) {
                        score += SCORE_PER_ASTEROID;
                    }
                    bulletIterator.remove();
                    destroyAsteroid(asteroidIterator, asteroid);
                    asteroidFound = true;
                }
            }
            if (!asteroidFound) {
                if (bullet.getX() < 0 || bullet.getX() > Asteroids.WORLD_WIDTH ||
                        bullet.getY() < 0 || bullet.getY() > Asteroids.WORLD_HEIGHT) {
                    bulletIterator.remove();
                }
            }
        }
        for (Asteroid asteroid : asteroidsToAdd) {
            asteroids.add(asteroid);
        }
        asteroidsToAdd.clear();
    }

    private void updateParticles(float dt) {
        Iterator<Particle> particleIterator = particles.iterator();
        while (particleIterator.hasNext()) {
            Particle particle = particleIterator.next();
            particle.update(dt);
            if (particle.shouldRemove()) {
                particleIterator.remove();
            }
        }
    }

    private void destroyAsteroid(Iterator<Asteroid> asteroidIterator, Asteroid asteroid) {
        Asteroids.content.playSound(ContentManager.SoundEffect.EXPLODE);
        addParticles(asteroid, ASTEROID_EXPLODE_PARTICLES);
        asteroidIterator.remove();
    }

    private void destroyShip() {
        addParticles(ship, SHIP_EXPLODE_PARTICLES);
        shipAlive = false;
        shipRespawnTimer.reset();
        lives--;
        Asteroids.content.playSound(ContentManager.SoundEffect.EXPLODE);
        Asteroids.content.stopSound(ContentManager.SoundEffect.THRUSTER);
    }

    private void addParticles(AbstractEntity entity, int amount) {
        for (int i = 0; i < amount; i++) {
            particles.add(new Particle(entity));
        }
    }

    private void reset() {
        Asteroids.content.stopSound(ContentManager.SoundEffect.LARGE_ENEMY);
        Asteroids.content.stopSound(ContentManager.SoundEffect.SMALL_ENEMY);
        ship.reset();
        asteroids.clear();
        bullets.clear();
        particles.clear();
        shipAlive = true;
        if(enemyContainer.isEnemyActive()) enemyContainer.deactivateEnemy();
        for (int i = 0; i < INITIAL_ASTEROIDS; i++) {
            float x = MyHelpers.choose(
                    MathUtils.random(Asteroids.WORLD_WIDTH * 0.3333f),
                    MathUtils.random(Asteroids.WORLD_WIDTH * 0.6666f, Asteroids.WORLD_WIDTH)
            );
            float y = MyHelpers.choose(
                    MathUtils.random(Asteroids.WORLD_HEIGHT * 0.3333f),
                    MathUtils.random(Asteroids.WORLD_HEIGHT * 0.6666f, Asteroids.WORLD_HEIGHT)
            );
            asteroids.add(new Asteroid(x, y));
        }
        asteroidSpawner.reset();
        enemySpawner.reset();
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setProjectionMatrix(viewport.getCamera().combined);
        for (Star star : stars) {
            star.draw(dt, sr);
        }
        for (Particle particle : particles) {
            particle.draw(dt, sr);
        }
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(dt, sr);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(dt, sr);
        }
        if(enemyContainer.isEnemyActive()) {
            enemyContainer.drawEnemy(dt, sr);
        }
        if (shipAlive) {
            ship.draw(dt, sr);
        }
        sr.end();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        hud.draw(sb);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {

    }
}
