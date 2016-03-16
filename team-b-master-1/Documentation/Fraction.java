//This is only the fraction class
//The tester with the main() is a separate file
public class Fraction {
	private int numerator;		//instant varialbles declared
	private int denominator;	//numerator and denominator

	public Fraction(int num, int denom) { //constructor
		numerator = num;
		denominator = denom;
	}

	public int getNumerator(){ //getter method
		return numerator;
	}
	public int getDenominator(){ //getter method
		return denominator;
	}

	public void setNumerator(int num) { //setter method
		numerator = num;
	}
	public void setDenominator(int num) { //setter method
		denominator = num;
	}

	public Fraction add(Fraction f) {
		//int num = numerator * f.getDenominator() + f.getNumerator() * denominator;
		//int denom = denominator * f.getDenominator();
		//Fraction result = new Fraction(num, denom);
		//return result;
		////or avoid the call of getDenominator and getNumerator, by having the 'add' method
		////   access f.numerator and f.denominator directly, since we are inside of the class is legal to do that...
		int num = numerator * f.denominator + f.numerator * denominator;
		int denom = denominator * f.denominator;
		return new Fraction(num, denom);
	}

	public Fraction sub(Fraction f) {
		int num = numerator * f.denominator - f.numerator * denominator;
		int denom = denominator * f.denominator;
		return new Fraction(num, denom);
	}

	public Fraction mul(Fraction f) {
		int num = numerator * f.numerator;
		int denom = denominator * f.denominator;
		return new Fraction(num, denom);
	}
	public Fraction div(Fraction f) {
		int num = numerator * f.denominator;
		int denom = denominator * f.numerator;
		return new Fraction(num, denom);
	}
}