package com.tcg.asteroids.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.asteroids.Asteroids;
import com.tcg.asteroids.MyHelpers;
import com.tcg.asteroids.entities.Asteroid;
import com.tcg.asteroids.entities.Bullet;
import com.tcg.asteroids.entities.Ship;
import com.tcg.asteroids.entities.Star;
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

    private static final int NUM_STARS = 100;
    private static final float PULSE_TIME = 0.75f;

    private Viewport viewport;
    private Ship ship;

    private List<Asteroid> asteroids;
    private Timer asteroidSpawner;

    private List<Star> stars;

    private ContentManager.SoundEffect pulse;
    private Timer pulseTimer;

    private List<Bullet> bullets;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        viewport = new FitViewport(Asteroids.WORLD_WIDTH, Asteroids.WORLD_HEIGHT);
        ship = new Ship();
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

        reset();
    }

    @Override
    public void handleInput(float dt) {
        ship.handleInput(dt);
        if (MyInput.keyCheckPressed(MyInput.SHOOT)) {
            Asteroids.content.playSound(ContentManager.SoundEffect.SHOOT);
            bullets.add(new Bullet(ship));
        }
    }

    @Override
    public void update(float dt) {
        viewport.apply(true);
        ship.update(dt);
        asteroidSpawner.update(dt);
        pulseTimer.update(dt);
        updateAsteroids(dt);
        updateBullets(dt);
    }

    private void updateAsteroids(float dt) {
        for (Asteroid asteroid : asteroids) {
            asteroid.update(dt);
        }
    }

    private void updateBullets(float dt) {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        List<Asteroid> asteroidsToAdd = new ArrayList<Asteroid>();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update(dt);
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
                    bulletIterator.remove();
                    asteroidIterator.remove();
                    asteroidFound = true;
                }
            }
            if(!asteroidFound) {
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

    private void reset() {
        ship.reset();
        asteroids.clear();
        bullets.clear();
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
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setProjectionMatrix(viewport.getCamera().combined);
        for (Star star : stars) {
            star.draw(dt, sr);
        }
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(dt, sr);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(dt, sr);
        }
        ship.draw(dt, sr);
        sr.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {

    }
}
