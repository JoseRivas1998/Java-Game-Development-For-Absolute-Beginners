package com.tcg.platformer.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class ContentManager implements Disposable {

    public enum Font {
        MAIN(
                "fnt/roboto.ttf",
                48,
                Color.WHITE,
                1,
                Color.BLACK,
                1,
                1,
                new Color(0xd2d2d2d2)
        );
        public final String path;
        public final int fontSize;
        public final Color fontColor;
        public final float borderWidth;
        public final Color borderColor;
        public final int shadowOffsetX;
        public final int shadowOffsetY;
        public final Color shadowColor;

        Font(String path, int fontSize, Color fontColor, float borderWidth, Color borderColor, int shadowOffsetX, int shadowOffsetY, Color shadowColor) {
            this.path = path;
            this.fontSize = fontSize;
            this.fontColor = fontColor;
            this.borderWidth = borderWidth;
            this.borderColor = borderColor;
            this.shadowOffsetX = shadowOffsetX;
            this.shadowOffsetY = shadowOffsetY;
            this.shadowColor = shadowColor;
        }

        public FreeTypeFontGenerator.FreeTypeFontParameter toParam() {
            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
            param.size = this.fontSize;
            param.color = this.fontColor;
            if (this.borderWidth > 0) {
                param.borderWidth = this.borderWidth;
                param.borderColor = this.borderColor;
            }
            if (this.shadowOffsetX > 0 || this.shadowOffsetY > 0) {
                param.shadowOffsetX = this.shadowOffsetX;
                param.shadowOffsetY = this.shadowOffsetY;
                param.shadowColor = this.shadowColor;
            }
            return param;
        }

    }

    public enum SoundEffect {
        COIN("snd/coin.mp3"),
        JUMP("snd/jump.mp3");
        public final String path;

        SoundEffect(String path) {
            this.path = path;
        }
    }

    public enum Image {
        PINK_ALIEN_WALK("img/pinkAlien_walk.png"),
        PINK_ALIEN_STAND("img/pinkAlien_stand.png"),
        PINK_ALIEN_JUMP("img/pinkAlien_jump.png"),
        LEVEL_BG("img/levelbg.png"),
        COIN("img/coin.png")
        ;
        public final String path;

        Image(String path) {
            this.path = path;
        }
    }

    public enum TmxMap {
        LEVEL_1("maps/map01.tmx");
        public final String path;

        TmxMap(String path) {
            this.path = path;
        }
    }

    private Map<Font, BitmapFont> fonts;
    private Map<SoundEffect, Sound> sounds;
    private Map<Image, Texture> textures;
    private Map<TmxMap, TiledMap> tmxMaps;

    private GlyphLayout gl;

    public ContentManager() {
        loadFonts();
        loadSounds();
        loadTextures();
        loadTmxMaps();
    }

    private void loadTmxMaps() {
        tmxMaps = new HashMap<TmxMap, TiledMap>();
        TmxMapLoader loader = new TmxMapLoader();
        for (TmxMap value : TmxMap.values()) {
            tmxMaps.put(value, loader.load(value.path));
        }
    }

    private void loadTextures() {
        textures = new HashMap<Image, Texture>();
        for (Image value : Image.values()) {
            textures.put(value, new Texture(value.path));
        }
    }

    private void loadSounds() {
        sounds = new HashMap<SoundEffect, Sound>();
        for (SoundEffect value : SoundEffect.values()) {
            sounds.put(value, Gdx.audio.newSound(Gdx.files.internal(value.path)));
        }
    }

    private void loadFonts() {
        fonts = new HashMap<Font, BitmapFont>();
        for (Font value : Font.values()) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(value.path));
            FreeTypeFontGenerator.FreeTypeFontParameter param = value.toParam();
            param.borderStraight = true;
            fonts.put(value, generator.generateFont(param));
            generator.dispose();
        }
        gl = new GlyphLayout();
    }

    public BitmapFont getFont(Font font) {
        return fonts.get(font);
    }

    public float getWidth(Font font, String s) {
        gl.setText(getFont(font), s);
        return gl.width;
    }

    public float getWidth(Font font, String s, float targetWidth, int halign, boolean wrap) {
        gl.setText(getFont(font), s, getFont(font).getColor(), targetWidth, halign, wrap);
        return gl.width;
    }

    public float getHeight(Font font, String s) {
        gl.setText(getFont(font), s);
        return gl.height;
    }

    public float getHeight(Font font, String s, float targetWidth, int halign, boolean wrap) {
        gl.setText(getFont(font), s, getFont(font).getColor(), targetWidth, halign, wrap);
        return gl.height;
    }

    public void playSound(SoundEffect soundEffect) {
        sounds.get(soundEffect).play();
    }

    public void playSound(SoundEffect soundEffect, float volume) {
        sounds.get(soundEffect).play(volume);
    }

    public void playSound(SoundEffect soundEffect, float volume, float pitch, float pan) {
        sounds.get(soundEffect).play(volume, pitch, pan);
    }

    public void loopSound(SoundEffect soundEffect) {
        sounds.get(soundEffect).loop();
    }

    public void loopSound(SoundEffect soundEffect, float volume) {
        sounds.get(soundEffect).loop(volume);
    }

    public void loopSound(SoundEffect soundEffect, float volume, float pitch, float pan) {
        sounds.get(soundEffect).loop(volume, pitch, pan);
    }

    public void stopSound(SoundEffect soundEffect) {
        sounds.get(soundEffect).stop();
    }

    public void stopAllSound(SoundEffect soundEffect) {
        for (SoundEffect value : SoundEffect.values()) {
            stopSound(value);
        }
    }

    public Texture getTexture(Image image) {
        return textures.get(image);
    }

    public TextureRegion getTextureRegion(Image image) {
        return new TextureRegion(getTexture(image));
    }

    public TiledMap getMap(TmxMap tmxMap) {
        return tmxMaps.get(tmxMap);
    }

    public float mapWidthPixels(TmxMap tmxMap) {
        return tileWidth(tmxMap) * getMap(tmxMap).getProperties().get("width", Integer.class);
    }

    public float mapHeightPixels(TmxMap tmxMap) {
        return tileWidth(tmxMap) * getMap(tmxMap).getProperties().get("height", Integer.class);
    }

    public int tileWidth(TmxMap tmxMap) {
        return getMap(tmxMap).getProperties().get("tilewidth", Integer.class);
    }

    public int tileHeight(TmxMap tmxMap) {
        return getMap(tmxMap).getProperties().get("tileheight", Integer.class);
    }

    @Override
    public void dispose() {
        for (Font value : Font.values()) {
            fonts.get(value).dispose();
        }
        for (SoundEffect value : SoundEffect.values()) {
            sounds.get(value).dispose();
        }
        for (Image value : Image.values()) {
            textures.get(value).dispose();
        }
        for (TmxMap value : TmxMap.values()) {
            tmxMaps.get(value).dispose();
        }
    }
}
