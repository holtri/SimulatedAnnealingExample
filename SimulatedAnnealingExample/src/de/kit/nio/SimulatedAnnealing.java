package de.kit.nio;

public class SimulatedAnnealing {

	private static double calculateFunctionValue(TestFunction function,
			Solution solution) {
		return function.f(solution.getX(), solution.getY());
	}

	public static void main(String[] args) {
		
		TestFunction testFunction = setupFunctions();
		
		System.out.println("n\tx-cor\ty-cor\tf(x,y)\tt-max");
		
		for (int n = 1; n <= 10; n++) {
			startSimulationRuns(testFunction, n);
		}
	}

	private static void startSimulationRuns(TestFunction testFunction, int n) {
		int t = 0;
		Solution currentSolution;
		currentSolution = new Solution(-5.12 + Math.random()
				* (5.12 * 2), -5.12 + Math.random() * (5.12 * 2));
		double currentEnergy;
		currentEnergy = calculateFunctionValue(testFunction,
				currentSolution);
		
		Solution bestSolution = currentSolution;
		double bestEnergy = currentEnergy;
		
		double T = 180;
		double alpha = 0.9;
		double distance = 0.1;
		
		runSample(testFunction, n, t, currentSolution, currentEnergy,
				bestSolution, bestEnergy, T, alpha, distance);
	}

	private static void runSample(TestFunction testFunction, int n, int t,
			Solution currentSolution, double currentEnergy,
			Solution bestSolution, double bestEnergy, double T, double alpha,
			double distance) {
		for (int maxt = 10; maxt <= 1000000; maxt = maxt * 10) {
			bestSolution = runSimulatedAnnealing(testFunction, t,
					currentSolution, currentEnergy, bestSolution,
					bestEnergy, T, alpha, distance, maxt);
			System.out.printf("%d\t%f\t%f\t%f\t%d \n", n,bestSolution.getX(),
					bestSolution.getY(),
					calculateFunctionValue(testFunction, bestSolution),
					maxt);
		}
	}

	private static TestFunction setupFunctions() {
		TestFunction deJong = (x, y) -> Math.pow(x, 2) + Math.pow(y, 2);
		TestFunction hyperEllepsoid = (x, y) -> Math.pow(x, 2) + 2
				* Math.pow(y, 2);
		TestFunction rotatedHyperEllepsoid = (x, y) -> 2 * Math.pow(x, 2)
				+ Math.pow(y, 2);
		TestFunction rosenbrockValley = (x, y) -> 100
				* Math.pow((y - Math.pow(x, 2)), 2) + Math.pow((1 - x), 2);
		TestFunction rastrigrin = (x, y) -> 20
				+ (Math.pow(x, 2) - 10 * Math.cos(2 * Math.PI * x))
				+ (Math.pow(y, 2) - 10 * Math.cos(2 * Math.PI * y));
		TestFunction testFunction = deJong;
		return testFunction;
	}

	private static Solution runSimulatedAnnealing(TestFunction testFunction,
			int t, Solution currentSolution, double currentEnergy,
			Solution bestSolution, double bestEnergy, double T, double alpha,
			double distance, int maxt) {
		Solution newSolution;
		double newEnergy;
		while (t < maxt) {
			newSolution = currentSolution.getRandomNeighbor(distance);
			newEnergy = calculateFunctionValue(testFunction,
					newSolution);
			if (newEnergy <= currentEnergy) {
				currentSolution = newSolution;
				currentEnergy = newEnergy;
			} else {
				if (Math.random() < Math
						.exp((currentEnergy - newEnergy)) / T) {
					currentSolution = newSolution;
					currentEnergy = newEnergy;
				}
			}
			if (currentEnergy < bestEnergy) {
				bestSolution = currentSolution;
				bestEnergy = currentEnergy;
			}

			T = alpha * T;
			t++;
		}
		return bestSolution;
	}

}
