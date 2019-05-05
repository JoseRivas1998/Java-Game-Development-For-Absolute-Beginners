package com.tcg.platformer.gamestates;

public enum GameStateType {
    TITLE(TitleState.class),
    PLAY(PlayState.class);
    public final Class<? extends AbstractGameState> stateClass;

    GameStateType(Class<? extends AbstractGameState> stateClass) {
        this.stateClass = stateClass;
    }
}
