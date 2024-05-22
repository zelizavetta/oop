package nsu.fit.ezaitseva.snakegame.genetic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import nsu.fit.ezaitseva.snakegame.fx.GlobalGameSettings;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.players.CustomizableEuristickBot;

/**
 * class describing genetic algorithm for snakes.
 */
public class GeneticAlgorithm {
  @JsonProperty("genes")
  private Double[][] populations;

  @JsonIgnore private final Game gameForCopy;
  private final Random random = new Random();
  private final int snakeAmount;
  private final double crossingOverChance = 0.8;
  private final double mutationChance = 0.1;

  /** Hall of Fame. */
  int sizeOfHof = 10;

  public GeneticAlgorithm() {
    this(GlobalGameSettings.gameSettings.getGame().getCopy());
  }

  public GeneticAlgorithm(Game gameForCopy) {
    this.gameForCopy = gameForCopy;
    snakeAmount = gameForCopy.getSnakeMap().size();
  }

  public List<Individual> generateGenes() {
    CustomizableEuristickBot initialBot = new CustomizableEuristickBot(gameForCopy, 0);
    Double[] initialCoefs = initialBot.getCoefficientArr();
    int chromosomeSize = initialCoefs.length;

    int populationSize = snakeAmount * 20;

    Individual[] individuals = new Individual[populationSize];
    for (int i = 0; i < individuals.length; i++) {
      Double[] chromosome = new Double[chromosomeSize];
      for (int j = 0; j < chromosome.length; j++) {
        chromosome[j] = random.nextDouble(-1.5, 1.5);
      }
      Individual individual = new Individual(chromosome, i);
      individuals[i] = individual;
    }
    System.out.println(individuals.length);
    int maxRounds = 100;
    for (int curRound = 0; curRound < maxRounds; curRound++) {
      Map<Integer, Integer> fitMap = fitness(individuals);
      System.out.println("listOfFitness: " + fitMap);
      System.out.println(
          "Best: "
              + Arrays.stream(individuals)
                  .sorted(Comparator.comparingInt((individual -> -fitMap.get(individual.id))))
                  .limit(10)
                  .map((individual -> Map.entry(individual.id, fitMap.get(individual.id))))
                  .toList());
      System.out.println(
          "Best: "
              + Arrays.stream(individuals)
                  .sorted(Comparator.comparingInt((individual -> -fitMap.get(individual.id))))
                  .limit(2)
                  .map(individual -> Arrays.toString(individual.genes))
                  .toList());
      individuals = selection(individuals, fitMap);
      // individuals = populationCrossingOver(individuals);
      individuals = populationMutation(individuals);
    }
    return Arrays.stream(individuals).toList();
  }

  private Map<Integer, Integer> fitness(Individual[] individuals) {
    return averageFitnessOfPopulation(individuals, gameForCopy, 6);
  }

  private Individual[] selection(Individual[] individuals, Map<Integer, Integer> fitnessMap) {

    Individual[] nextPopulation = new Individual[individuals.length];
    Individual[] tmp = new Individual[5];
    var copy = Arrays.copyOf(individuals, individuals.length);
    Arrays.sort(copy, Comparator.comparingInt((individual -> -fitnessMap.get(individual.id))));

    for (int i = 0; i < sizeOfHof; i++) {
      nextPopulation[i] = new Individual(copy[i].genes, i);
    }

    for (int i = sizeOfHof; i < individuals.length; i++) {
      for (int j = 0; j < tmp.length; j++) {
        tmp[j] = individuals[random.nextInt(individuals.length)];
      }
      Arrays.sort(tmp, Comparator.comparingInt(ind -> -fitnessMap.get(ind.id)));
      nextPopulation[i] = new Individual(tmp[0].genes, i);
    }

    return nextPopulation;
  }

  private Individual[] populationCrossingOver(Individual[] individuals) {
    for (int i = sizeOfHof + 1; i < individuals.length; i += 2) {
      pairCrossingOver(individuals[i - 1], individuals[i]);
    }
    return individuals;
  }

  private void pairCrossingOver(Individual individual1, Individual individual2) {
    if (random.nextDouble() > crossingOverChance) {
      return;
    }
    Double[] genes1 = individual1.genes;
    Double[] genes2 = individual2.genes;
    double alpha = 0;
    for (int i = 0; i < genes1.length; i++) {
      if (random.nextDouble() < 0.25) {
        continue;
      }
      double g1 = genes1[i];
      double g2 = genes2[i];
      if (g1 > g2) {
        double tmp = g1;
        g1 = g2;
        g2 = tmp;
      }
      double lowBound = g1 - alpha * (g2 - g1);
      double upBound = g2 + alpha * (g2 - g1);
      double newGen1 = random.nextDouble(lowBound, upBound + 0.1);
      double newGen2 = random.nextDouble(lowBound, upBound + 0.1);
      genes1[i] = newGen1;
      genes2[i] = newGen2;
    }
  }

  private Individual[] populationMutation(Individual[] individuals) {
    for (int i = sizeOfHof; i < individuals.length; i++) {
      individualMutation(individuals[i]);
    }

    return individuals;
  }

  private void individualMutation(Individual individual) {
    Double[] genes = individual.genes;
    double chance = mutationChance / genes.length;
    for (int i = 0; i < genes.length; i++) {
      if (random.nextDouble() < chance) {
        double gen = genes[i];
        genes[i] =
            (random.nextDouble(gen - 0.05, gen + 0.05) + random.nextDouble(gen - .05, gen + .05))
                / 2;
      }
    }
  }

  private Map<Integer, Integer> averageFitnessOfPopulation(
      Individual[] individuals, Game game, int amount) {
    Map<Integer, Integer> avgFitnessMap = new HashMap<>();
    for (int i = 0; i < individuals.length; i++) {
      avgFitnessMap.put(i, 0);
    }

    for (int i = 0; i < amount; i++) {
      fitnessOfPopulation(individuals, game)
          .forEach(
              (id, score) -> {
                avgFitnessMap.put(id, avgFitnessMap.get(id) + score);
              });
    }
    avgFitnessMap.forEach(
        (id, score) -> {
          avgFitnessMap.put(id, score / amount);
        });
    return avgFitnessMap;
  }

  private Map<Integer, Integer> fitnessOfPopulation(Individual[] individuals, Game game) {
    Map<Integer, Integer> fitnessMap = new HashMap<>();
    Individual[] group = new Individual[snakeAmount];
    for (int i = 0; i < individuals.length; i += snakeAmount) {
      System.arraycopy(individuals, i, group, 0, snakeAmount);
      for (Map.Entry<Integer, Integer> pair : fitnessOfGroup(group, game).entrySet()) {
        fitnessMap.put(individuals[pair.getKey() + i].id, pair.getValue());
      }
    }
    return fitnessMap;
  }

  /**
   * Return id of individual to fitness (size at the end of game).
   *
   * @param group group
   * @param game game
   * @return individual to fitness(size at the end of game)
   */
  private Map<Integer, Integer> fitnessOfGroup(Individual[] group, Game game) {
    game = game.getCopy();
    for (int i = 0; i < group.length; i++) {
      CustomizableEuristickBot euristickBot = new CustomizableEuristickBot(game, i);
      euristickBot.setCoefficient(group[i].genes);
      game.addPlayer(i, euristickBot);
    }
    int steps = 2000;
    while (game.tick()) {
      if (--steps < 0) {
        break;
      }
    }

    return game.getResults();
  }

  /**
   * for individual.
   */
  public static class Individual {
    private int id;
    private Double[] genes;

    public Individual(Double[] genes, int id) {
      this.genes = genes;
      this.id = id;
    }

    public Individual(int size, int id) {
      this.genes = new Double[size];
      this.id = id;
    }

    public Double[] getGenes() {
      return genes;
    }

    public void setGenes(Double[] genes) {
      this.genes = genes;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Individual that = (Individual) o;
      return id == that.id;
    }

    @Override
    public int hashCode() {
      return id;
    }

    @Override
    public String toString() {
      return "Individual{" + "id=" + id + ", genes=" + Arrays.toString(genes) + '}';
    }
  }

  /**
   * main in genetic algorithm.
   * 
   * @param args args for main
   */
  public static void main(String[] args) {
    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

    System.out.println(geneticAlgorithm.generateGenes());
  }
}
