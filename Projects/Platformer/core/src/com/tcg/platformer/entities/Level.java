package com.tcg.platformer.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

    private static final String GROUND_LAYER = "ground";

    public Level(int level, World world) {
        ContentManager.TmxMap tmxMap = ContentManager.TmxMap.values()[level];
        tiledMap = Platformer.content.getMap(tmxMap);
        tileWidth = Platformer.content.tileWidth(tmxMap);
        tileHeight = Platformer.content.tileHeight(tmxMap);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        loadGround(world);
    }

    private void loadGround(World world) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(GROUND_LAYER);
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);

                if (cell == null || cell.getTile() == null) continue;

                float x = (col * tileWidth) * METERS_PER_PIXEL;
                float y = (row * tileHeight) * METERS_PER_PIXEL;
                float width = tileWidth * METERS_PER_PIXEL;
                float height = tileHeight * METERS_PER_PIXEL;

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(x + (width * 0.5f), y + (height * 0.5f));
                Body body = world.createBody(bodyDef);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(width * 0.5f, height * .5f);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.filter.categoryBits = PhysicsLayers.GROUND;
                fixtureDef.filter.maskBits = PhysicsLayers.PLAYER;
                fixtureDef.shape = shape;
                body.createFixture(fixtureDef);
                shape.dispose();
            }
        }
    }

    public void render(Viewport viewport) {
        tiledMapRenderer.setView((OrthographicCamera) viewport.getCamera());
        tiledMapRenderer.render();
    }


}
