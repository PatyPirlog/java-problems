import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Bani {
	static class Task {
		public static final String INPUT_FILE = "bani.in";
		public static final String OUTPUT_FILE = "bani.out";
		// in N stochez nr de bancnote ce trebuie asezate
		// int type stochez tipul instructiunilor de sortare
		int N, type;
		private static final int MOD = 1000000007;
		private static final int NR_BANCNOTES = 5;

		private void readInput() {
			try {
				BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
				String line;
				line = br.readLine();
				String[] ints = line.split(" ");

				type = Integer.parseInt(ints[0]);
				N = Integer.parseInt(ints[1]);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// functie ce calculeaza ridicarea la putere modularizata a doua numere
		// se foloseste pt calcularea rezultatului din functia typeOne()
		private long exponent(long exp, long base) {
			long result = 1L;

			while (exp > 0) {
				if (exp % 2 == 1) {
					result = result * base % MOD;
				}
				exp /= 2;
				base = base * base % MOD;
			}
			return result;
		}

		// functie ce calculeaza rezultatul pentru tipul de instructiuni 1
		// rezultatul se calculeaza dupa formula NR_BANCNOTES * 2 ^ (N-1)
		private long typeOne() {
			// rezultatul este initial 5 pentru ca fiecare dintre cele 5 bancnote
			// se poate aranja initial intr-un singur mod
			long result = 5;
			result = (result * exponent(N - 1, 2)) % MOD;

			return result;
		}

		// functie ce calculeaza rezultatul pentru tipul de intructiuni 2
		private long typeTwo() {
			// ma folosesc de matricea de forma dp[i][j]
			// i reprezinta tipul bancnotei iar j numarul de bancnote
			long[][] dp = new long[NR_BANCNOTES + 1][N + 1];

			// fiecare bancnota are asociat cate un indice i = [0,4]
			// pentru 10 -> i = 0, 50 -> i = 1, 100 -> i = 2
			// 200 -> i = 3, 500 -> i = 4

			// cazul de baza -> o bancnota de indice i poate fi aseazata intr-un singur mod
			for (int i = 0; i < NR_BANCNOTES; i++) {
				dp[i][0] = 1;
			}

			// calculez fiecare caz pentru fiecare tip de moneda
			// se tine cont de pasul anterior j-1 si de bancnotele ce pot precede bancnota i
			for (int j = 1; j < N; j++) {

				// pt bancnota de 10, bancnotele sunt 50, 100 si 500
				dp[0][j] = (dp[1][j - 1] + dp[2][j - 1] + dp[4][j - 1]) % MOD;

				// pt bancnota de 50, bancnotele sunt 10 si 200
				dp[1][j] = (dp[0][j - 1] + dp[3][j - 1]) % MOD;

				// pt bancnota de 100, bancnotele sunt 10, 100, 200
				dp[2][j] = (dp[0][j - 1] + dp[2][j - 1] + dp[3][j - 1]) % MOD;

				// pt bancnota de 200, bancnotele sunt 50 si 500
				dp[3][j] = (dp[1][j - 1] + dp[4][j - 1]) % MOD;

				// pt bancnota de 500, bancnota este 200
				dp[4][j] = dp[3][j - 1] % MOD;
			}

			// rezultatul final se va afla in dp[NR_BANCNOTES][N]
			// se formeaza tinand cont de pasul N-1 pentru toate cele 5 bancnote
			for (int i = 0; i < NR_BANCNOTES; i++) {
				dp[NR_BANCNOTES][N] = (dp[NR_BANCNOTES][N] + dp[i][N - 1]) % MOD;
			}

			return dp[NR_BANCNOTES][N] % MOD;
		}

		private long money() {
			long result = 0;

			if (type == 1) {
				result = typeOne();
			} else if (type == 2) {
				result = typeTwo();
			}
			return result;
		}

		private void writeOutput(long result) {
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
			writeOutput(money());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}

