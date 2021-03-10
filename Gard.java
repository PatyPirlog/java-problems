import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Gard {
	// clasa folosita pentru a retine capetele bucatilor de gard
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
		public static final String INPUT_FILE = "gard.in";
		public static final String OUTPUT_FILE = "gard.out";
		private static final int MOD = 1000000007;
		int N;
		// retin bucatile de gard in vectorul vect
		Vector<Pair> vect = new Vector<Pair>();

		private void readInput() {
			try {
				BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));

				String line;
				String[] ints;
				N = Integer.parseInt(br.readLine());

				for (int i = 0; i < N; i++) {
					line = br.readLine();
					ints = line.split(" ");
					vect.add(new Pair(Integer.parseInt(ints[0]), Integer.parseInt(ints[1])));
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public int redundant() {
			// sortez bucatile de gard in ordine crescatoare in functie de x_start
			// daca cei doi x_start sunt egali, sortez descrescator dupa x_finish
			Collections.sort(vect, new Comparator<Pair>() {
				@Override
				public int compare(Pair p1, Pair p2) {
					if (p1.getX_start() == p2.getX_start()) {
						return p2.getX_finish() - p1.getX_finish();
					} else {
						return p1.getX_start() - p2.getX_start();
					}				
				}
			});
			// voi retine numarul de bucati redundante in result
			int result = 0;
			// max initial va fi primul x_finish
			int max = vect.get(0).getX_finish();

			// fiind sortate dupa x_start, de fiecare data cand se intalneste o bucata
			// cu x_finish mai mic decat max, atunci acea bucata automat este redundanta
			// max fiind mereu cea mai mare valoare posibila pt x_finish pana la pasul i
			for (int i = 1; i < N; i++) {
				if (vect.get(i).getX_finish() <= max) {
					// daca gasesc o bucata cu x_finish mai mica decat max
					// atunci aceasta este redundanta si se creste contorul
					result++;
				} else {
					// actualizez max de fiecare data cand gasesc un x_finish mai mare
					max = vect.get(i).getX_finish();
				}
			}
			return result;
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
			writeOutput(redundant());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}

