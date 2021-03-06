package com.tcg.clickyplane.gamestates;

public enum GameStateType {
    PLAY(PlayState.class),
    TITLE(TitleState.class);
    public final Class<? extends AbstractGameState> stateClass;

    GameStateType(Class<? extends AbstractGameState> stateClass) {
        this.stateClass = stateClass;
    }
}
