package com.tcg.clickyplane.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

import java.util.EnumMap;
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
            param.borderWidth = this.borderWidth;
            param.borderColor = this.borderColor;
            param.shadowOffsetX = this.shadowOffsetX;
            param.shadowOffsetY = this.shadowOffsetY;
            param.shadowColor = this.shadowColor;
            return param;
        }

    }

    private Map<Font, BitmapFont> fonts;

    private GlyphLayout gl;

    public ContentManager() {
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

    @Override
    public void dispose() {
        for (Font value : Font.values()) {
            fonts.get(value).dispose();
        }
    }
}
