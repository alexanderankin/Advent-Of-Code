public class Display {
  public static final int WIDE = 50;
  public static final int TALL = 6;

  private boolean pixels[][];

  public Display () {
    this.pixels = new boolean[TALL][WIDE];
  }

  public void rect(int a, int b) {
    int c;
    while (b-- > 0) {
      c = a;
      while (c-- >0) {
        this.pixels[b][c] = true;
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (boolean row[] : this.pixels) {
      for (boolean col : row) {
        sb.append(col ? '#' : '-');
      }
      sb.append('\n');
    }
    sb.append('\n');

    return sb.toString();
  }

  public String cleanToString() {
    StringBuilder sb = new StringBuilder();
    for (boolean row[] : this.pixels) {
      for (boolean col : row) {
        sb.append(col ? '#' : ' ');
      }
      sb.append('\n');
    }
    sb.append('\n');

    return sb.toString();
  }

  public void rotateRow(int row_num, int length) {
    boolean row[] = this.pixels[row_num];
    row = rotateBooleanArray(row, length);
    this.pixels[row_num] = row;
  }

  private boolean[] rotateBooleanArray(boolean arr[], int n) {
    // ((i + n) % length) // new location

    int length = arr.length;
    boolean newarr[] = new boolean[length];

    for (int i = 0; i < length; i++) {
      newarr[((i + n) % length)] = arr[i];
    }

    return newarr;
  }

  public void rotateColumn(int col_num, int n) {
    boolean oldcol[] = new boolean[this.pixels.length];

    for (int i = 0; i < this.pixels.length; i++) {
      oldcol[i] = this.pixels[i][col_num];
    }


    for (int i = 0; i < this.pixels.length; i++) {
      this.pixels[((i + n) % this.pixels.length)][col_num] = oldcol[i];
    }
  }

  public int getPixelOnCount() {
    int count = 0;

    for (boolean[] row : this.pixels) {
      for (boolean td : row) {
        if (td) {
          count++;
        }
      }
    }

    return count;
  }
}
