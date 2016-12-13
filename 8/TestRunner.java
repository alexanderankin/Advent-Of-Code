public class TestRunner {
	public static void main(String[] args) {
		Display d = new Display();

		System.out.println(d.toString());
		d.rect(3, 2);
		System.out.println(d.toString());
		d.rotateRow(0, 50);
		System.out.println(d.toString());
		d.rotateColumn(0, 9);
		System.out.println(d.toString());
	}
}