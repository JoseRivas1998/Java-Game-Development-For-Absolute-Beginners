package com.tcg.platformer.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.platformer.Platformer;
import com.tcg.platformer.managers.ContentManager;

import static com.tcg.platformer.GameData.*;

public class Level {

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private int tileWidth;
    private int tileHeight;

    private Vector2 playerSpawnPosition;
    private Vector2 topRight;

    private static final String GROUND_LAYER = "ground";
    private static final String COLLISION_LAYER = "collision";
    private static final String START_POS_LAYER = "player_start";

    public Level(int level, World world) {
        ContentManager.TmxMap tmxMap = ContentManager.TmxMap.values()[level];
        tiledMap = Platformer.content.getMap(tmxMap);
        tileWidth = Platformer.content.tileWidth(tmxMap);
        tileHeight = Platformer.content.tileHeight(tmxMap);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        topRight = new Vector2(Platformer.content.mapWidthPixels(tmxMap), Platformer.content.mapHeightPixels(tmxMap));
        loadGround(world);
        loadStartingPosition(world);
    }

    private void loadGround(World world) {
        MapLayer collisionLayer = tiledMap.getLayers().get(COLLISION_LAYER);
        MapObjects objects = collisionLayer.getObjects();
        for (MapObject object : objects) {
            float[] vertices = new float[0];
            float x = 0;
            float y = 0;
            if(object instanceof PolygonMapObject) {
                PolygonMapObject polygonMapObject = (PolygonMapObject) object;
                Polygon polygon = polygonMapObject.getPolygon();
                vertices = new float[polygon.getVertices().length];
                for (int i = 0; i < vertices.length; i++) {
                    vertices[i] = polygon.getVertices()[i] * METERS_PER_PIXEL;
                }
                x = polygon.getX();
                y = polygon.getY();
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
            polygonShape.createChain(vertices);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            fixtureDef.friction = 0;
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


}
