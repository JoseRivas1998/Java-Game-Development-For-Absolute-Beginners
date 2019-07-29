package com.tcg.asteroids.gamestates;

public enum GameStateType {
    TITLE(TitleState.class),
    PLAY(PlayState.class),
    WIN(WinState.class),
    GAME_OVER(GameOverState.class);
    public final Class<? extends AbstractGameState> stateClass;

    GameStateType(Class<? extends AbstractGameState> stateClass) {
        this.stateClass = stateClass;
    }
}
