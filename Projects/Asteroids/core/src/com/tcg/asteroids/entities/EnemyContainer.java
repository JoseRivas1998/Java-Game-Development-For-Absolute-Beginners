package com.tcg.asteroids.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class EnemyContainer {

    private Enemy enemy;
    private boolean enemyActive;

    public EnemyContainer() {
        this.enemy = null;
        this.enemyActive = false;
    }

    public void activateEnemy() {
        if(enemyActive) {
            throw new IllegalStateException("Enemy already active.");
        }
        enemy = new Enemy();
        enemyActive = true;
    }

    public Enemy deactivateEnemy() {
        ensureActiveEnemy();
        Enemy originalEnemy = this.enemy;
        this.enemy = null;
        this.enemyActive = false;
        return originalEnemy;
    }

    public boolean isEnemyActive() {
        return enemyActive;
    }

    public void updateEnemy(float dt) {
        ensureActiveEnemy();
        this.enemy.update(dt);
    }

    public Enemy.EnemyType getEnemyType() {
        ensureActiveEnemy();
        return enemy.getType();
    }

    public void drawEnemy(float dt, ShapeRenderer sr) {
        ensureActiveEnemy();
        enemy.draw(dt, sr);
    }

    public float getEnemyX() {
        ensureActiveEnemy();
        return enemy.getX();
    }

    public float getEnemyY() {
        ensureActiveEnemy();
        return enemy.getY();
    }

    public Vector2 getEnemyPosition() {
        return enemy.getPosition();
    }

    public boolean enemyCollidingWith(AbstractEntity e) {
        return enemy.collidingWith(e);
    }

    public Enemy getEnemy() {
        return enemy;
    }

    private void ensureActiveEnemy() {
        if(!enemyActive) {
            throw new IllegalStateException("Enemy is not active.");
        }
    }
}
