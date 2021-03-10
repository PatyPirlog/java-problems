import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class Bomboane {
	// clasa folosita pentru a retine intervalele pentru fiecare elev
	static class Pair {
		public int x_start;
		public int x_finish;

		public Pair(int x_start, int x_finish) {
			this.x_start = x_start;
			this.x_finish = x_finish;
		}

		public int getX_start() {
			return x_start;
		}

		public int getX_finish() {
			return x_finish;
		}

		public String toString() {
			return x_start + " " + x_finish;
		}
	}

	static class Task {
		public static final String INPUT_FILE = "bomboane.in";
		public static final String OUTPUT_FILE = "bomboane.out";
		private static final int MOD = 1000000007;
		// in N retin nr elevilor iar in M nr total de bomboane
		int N, M;
		// in vect retin intervalele corespunzatoare pt fiecare elev
		Vector<Pair> vect = new Vector<Pair>();
		// in sum retin suma capetelor superioare a intervalelor (Yi)
		int sum;

		private void readInput() {
			try {
				BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
				String line;
				String[] ints;
				String[] nm;

				line = br.readLine();
				nm = line.split(" ");

				N = Integer.parseInt(nm[0]);
				M = Integer.parseInt(nm[1]);

				for (int i = 0; i < N; i++) {
					line = br.readLine();
					ints = line.split(" ");

					sum = (sum + Integer.parseInt(ints[1])) % MOD;
					vect.add(new Pair(Integer.parseInt(ints[0]), Integer.parseInt(ints[1])));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public int bomboane() {
			// ma folosesc de matricea de forma dp[i][j]
			// reprezinta nr de moduri in care poti imparti la i elevi j bomboane
			int[][] dp = new int[N + 1][M + 1];

			// daca suma capetelor nu este macar egala cu M
			// atunci nu se pot imparti in niciun fel toate bomboanele la elevi
			if (sum < M) {
				return 0;
			} else if (sum == M) {
				// daca sum este egal cu M atunci exista un singur mod
				// se imparte fiecarui elev maximul din intervalul sau
				return 1;
			} else {
				// caz de baza, se pot imparti intr-un singur mod 0 bomboane la 0 elevi
				dp[0][0] = 1;

				for (int i = 1; i <= N; i++) {
					for (int j = 0; j <= M; j++) {
						// formez capetele intervalului
						// incepe de la nr de bomboane curent - inceputul intervalului elevului i
						// fac get(i-1) pt ca vect contine elevii de la i = 0 .. N-1
						int start = j - vect.get(i - 1).getX_start();

						// se termina la nr de bomboane curent - sfarsitul intervalului elevului i
						int finish = j - vect.get(i - 1).getX_finish();

						// iau doar cazurile cu bomboane necesare la pasul curent
						// daca startul e negativ atunci sar la urmatorul pas (j+1)
						if (start < 0) {
							continue;
						}

						// daca finish e negativ il fac 0
						if (finish < 0) {
							finish = 0;
						}

						// se pot imparti la i elevi j bomboane in functie de
						// modurile in care se pot imparti la i-1 elevi k bomboane
						for (int k = start; k >= finish; k--) {
							dp[i][j] = (dp[i][j] + dp[i - 1][k]) % MOD;
						}
					}
				}
			}
			// rezultatul va fi in dp[N][M]
			// in cate moduri se pot distribui celor N elevi M bomboane
			return dp[N][M] % MOD;
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException();
			}
		}

		public void solve() {
			readInput();
			writeOutput(bomboane());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}

