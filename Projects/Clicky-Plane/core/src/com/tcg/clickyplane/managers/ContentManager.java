package com.tcg.clickyplane.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class ContentManager implements Disposable {

    public enum Font {
        SCORE(
                "fnt/font.ttf", // font path
                56, // Font size in pixels
                Color.WHITE, // Font Color
                1f, // Border width
                Color.BLACK, // Border color
                2, 2, Color.BLACK // Shadow
        ),
        GAMEOVER_SCORE(
                "fnt/font.ttf", // font path
                48, // Font size in pixels
                Color.WHITE, // Font Color
                1f, // Border width
                Color.BLACK, // Border color
                2, 2, Color.BLACK // Shadow
        ),
        GAMEOVER_LABEL(
                "fnt/pixeled.ttf",
                24,
                new Color(0xF77958FF),
                0,
                Color.WHITE,
                0, 2, new Color(0xFCE5A4FF)

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
        DIE("snd/sfx_die.wav"),
        HIT("snd/sfx_hit.wav"),
        POINT("snd/sfx_point.wav"),
        SWOOSHING("snd/sfx_swooshing.wav"),
        WING("snd/sfx_wing.wav"),
        ;
        public final String path;

        SoundEffect(String path) {
            this.path = path;
        }
    }

    public enum Image {
        BACK("img/back.png"),
        BACKGROUND("img/background.png"),
        BUTTON("img/button.png"),
        GROUND_GRASS("img/ground_grass.png"),
        MEDALS("img/medals.png"),
        PIPE_BODY("img/pipe_body.png"),
        PIPE_TOP("img/pipe_top.png"),
        PLANE_SPRITESHEET("img/plane_spritesheet.png"),
        REPLAY("img/replay.png"),
        TAP_ANIMATION("img/tapAnimation.png"),
        TAP_LEFT("img/tapLeft.png"),
        TAP_RIGHT("img/tapRight.png"),
        TEXT_GAME_OVER("img/textGameOver.png"),
        TEXT_GET_READY("img/textGetReady.png"),
        UI_BG("img/ui_bg.png");
        public final String path;

        Image(String path) {
            this.path = path;
        }
    }

    private Map<Font, BitmapFont> fonts;
    private Map<SoundEffect, Sound> sounds;
    private Map<Image, Texture> textures;

    private GlyphLayout gl;

    public ContentManager() {
        loadFonts();
        loadSounds();
        loadTextures();
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
    }
}
