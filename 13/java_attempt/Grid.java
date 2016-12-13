public class Grid {
  public static final int SIZE = 50;

  private boolean grid[][];

  public Grid () {
    // this.grid = new boolean[SIZE - 30][SIZE];
    this.grid = new boolean[6][9];
    this.drawWalls();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (boolean row[] : this.grid) {
      for (boolean td : row) {
        sb.append(td ? '.' : '#');
      }
      sb.append('\n');
    }

    sb.append('\n');
    return sb.toString();
  }

  private void drawWalls() {
    for (int i = 0; i < this.grid.length; i++) {
      boolean[] row = this.grid[i];
      for (int j = 0; j < row.length; j++) {
        int x = j; int y = i;
        this.grid[y][x] = validCell(x, y);
      }
    }
  }

  private boolean validCell(int x, int y) {
    long result = x*x + 3*x + 2*x*y + y + y*y;
    result += 10;
    return Long.bitCount(result) % 2 == 0;
  }
}