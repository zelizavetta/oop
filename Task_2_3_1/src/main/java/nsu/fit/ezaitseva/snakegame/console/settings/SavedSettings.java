package nsu.fit.ezaitseva.snakegame.console.settings;

/**
 * saved settings.
 *
 * @param speed     speed of snake
 * @param userMode  user mode
 * @param difficult difficult of game
 */
public record SavedSettings(Integer speed, UserMode userMode, Double difficult) {
}
