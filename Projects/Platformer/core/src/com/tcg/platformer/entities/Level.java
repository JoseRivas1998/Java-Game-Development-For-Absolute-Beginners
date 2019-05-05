package com.tcg.platformer.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.platformer.Platformer;
import com.tcg.platformer.managers.ContentManager;

import java.util.ArrayList;
import java.util.List;

import static com.tcg.platformer.GameData.*;

public class Level {

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private int tileWidth;
    private int tileHeight;
    private List<AbstractB2DSpriteEntity> objects;

    private Vector2 playerSpawnPosition;
    private Vector2 topRight;

    private static final String GROUND_LAYER = "ground";
    private static final String COLLISION_LAYER = "collision";
    private static final String START_POS_LAYER = "player_start";
    private static final String COINS_LAYER = "coins";
    private static final String FLIES_LAYER = "flies";
    private static final String LEVEL_END_LAYER = "level_end";

    public Level(int level, World world) {
        loadLevel(level, world);
    }

    public boolean loadLevel(int level, World world) {
        if(level < ContentManager.TmxMap.values().length) {
            ContentManager.TmxMap tmxMap = ContentManager.TmxMap.values()[level];
            tiledMap = Platformer.content.getMap(tmxMap);
            tileWidth = Platformer.content.tileWidth(tmxMap);
            tileHeight = Platformer.content.tileHeight(tmxMap);
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
            topRight = new Vector2(Platformer.content.mapWidthPixels(tmxMap), Platformer.content.mapHeightPixels(tmxMap));
            objects = new ArrayList<AbstractB2DSpriteEntity>();
            loadGround(world);
            loadStartingPosition(world);
            loadCoins(world);
            loadFlies(world);
            loadLevelEnd(world);
            return true;
        }
        return false;
    }

    private void loadGround(World world) {
        MapLayer collisionLayer = tiledMap.getLayers().get(COLLISION_LAYER);
        MapObjects objects = collisionLayer.getObjects();
        for (MapObject object : objects) {
            float[] vertices = new float[0];
            float x = 0;
            float y = 0;
            boolean createLoop = false;
            if(object instanceof PolygonMapObject) {
                PolygonMapObject polygonMapObject = (PolygonMapObject) object;
                Polygon polygon = polygonMapObject.getPolygon();
                vertices = new float[polygon.getVertices().length];
                for (int i = 0; i < vertices.length; i++) {
                    vertices[i] = polygon.getVertices()[i] * METERS_PER_PIXEL;
                }
                x = polygon.getX();
                y = polygon.getY();
                createLoop = true;
            } else if(object instanceof PolylineMapObject) {
                PolylineMapObject polylineMapObject = (PolylineMapObject) object;
                Polyline polyline = polylineMapObject.getPolyline();
                vertices = new float[polyline.getVertices().length];
                for (int i = 0; i < vertices.length; i++) {
                    vertices[i] = polyline.getVertices()[i] * METERS_PER_PIXEL;
                }
                x = polyline.getX();
                y = polyline.getY();
            }
            BodyDef bodyDef = new BodyDef();
            bodyDef.fixedRotation = true;
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(x * METERS_PER_PIXEL, y * METERS_PER_PIXEL);
            Body body = world.createBody(bodyDef);

            ChainShape polygonShape = new ChainShape();
            if(createLoop) {
                polygonShape.createLoop(vertices);
            } else {
                polygonShape.createChain(vertices);
            }
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            fixtureDef.friction = 0;
            fixtureDef.filter.categoryBits = PhysicsLayers.GROUND;
            fixtureDef.filter.maskBits = -1; // -1 is the same thing as 1111 1111 1111 1111, meaning it can be with anything
            body.createFixture(fixtureDef);
            polygonShape.dispose();
        }
    }

    private void loadStartingPosition(World world) {
        MapLayer startPosLayer = tiledMap.getLayers().get(START_POS_LAYER);
        MapObjects mapObjects = startPosLayer.getObjects();
        RectangleMapObject spawn = (RectangleMapObject) mapObjects.get(0);
        Rectangle spawnRect = spawn.getRectangle();
        playerSpawnPosition = new Vector2(spawnRect.x * METERS_PER_PIXEL, spawnRect.y * METERS_PER_PIXEL);
    }

    private void loadCoins(World world) {
        MapLayer coinLayer = tiledMap.getLayers().get(COINS_LAYER);
        MapObjects mapObjects = coinLayer.getObjects();
        for (MapObject mapObject : mapObjects) {
            if(mapObject instanceof EllipseMapObject) {
                Ellipse ellipse = ((EllipseMapObject) mapObject).getEllipse();
                Vector2 spawnPoint = new Vector2(ellipse.x * METERS_PER_PIXEL, ellipse.y * METERS_PER_PIXEL);
                objects.add(new Coin(world, spawnPoint));
            }
        }
    }

    private void loadFlies(World world) {
        MapLayer flyLayer = tiledMap.getLayers().get(FLIES_LAYER);
        MapObjects mapObjects = flyLayer.getObjects();
        for(MapObject mapObject : mapObjects) {
            if(mapObject instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
                Vector2 spawnPoint = new Vector2(rect.x * METERS_PER_PIXEL, rect.y * METERS_PER_PIXEL);
                objects.add(new MeanFly(world, spawnPoint));
            }
        }
    }

    private void loadLevelEnd(World world) {
        MapLayer levelEndLayer = tiledMap.getLayers().get(LEVEL_END_LAYER);
        MapObjects mapObjects = levelEndLayer.getObjects();
        for (MapObject mapObject : mapObjects) {
            if(mapObject instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();

                float hWidth = (rect.width * METERS_PER_PIXEL) * 0.5f;
                float hHeight = (rect.height * METERS_PER_PIXEL) * 0.5f;

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(rect.x * METERS_PER_PIXEL + hWidth, rect.y * METERS_PER_PIXEL + hHeight);
                Body body = world.createBody(bodyDef);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(hWidth, hHeight);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.isSensor = true;
                fixtureDef.shape = shape;
                fixtureDef.filter.categoryBits = PhysicsLayers.LEVEL_END;
                fixtureDef.filter.maskBits = PhysicsLayers.PLAYER;
                Fixture fixture = body.createFixture(fixtureDef);
                fixture.setUserData(B2DUserData.LEVEL_END);
                shape.dispose();

            }
        }
    }

    public Vector2 getPlayerSpawnPosition() {
        return new Vector2(playerSpawnPosition);
    }

    public Vector2 getTopRight() {
        return new Vector2(topRight);
    }

    public void render(Viewport viewport) {
        tiledMapRenderer.setView((OrthographicCamera) viewport.getCamera());
        tiledMapRenderer.render();
    }

    public void remove(AbstractB2DSpriteEntity entity) {
        entity.dispose();
        objects.remove(entity);
    }

    public void renderObjects(float dt, SpriteBatch sb, ShapeRenderer sr) {
        for (AbstractB2DSpriteEntity object : objects) {
            object.draw(dt, sb, sr);
        }
    }

    public void updateObjects(float dt) {
        for (AbstractB2DSpriteEntity object : objects) {
            object.update(dt);
        }
    }

    public void addObject(AbstractB2DSpriteEntity entity) {
        objects.add(entity);
    }

    public void removeAllObjects() {
        while(!objects.isEmpty()) {
            remove(objects.get(0));
        }
    }

}
