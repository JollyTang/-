package atTang;

public class Block {
	double a = 0;
	double b = 0;
	double h = 0;
	double h_x = 0;
	
	double area = 0;
	double yc = 0;
	double Ic = 0;
	double Ix = 0;
	double S = 0;
	
	public Block(double a, double b, double h, double h_x) {
		super();
		this.a = a;
		this.b = b;
		this.h = h;
		this.h_x = h_x;
		
		area = 0.5 * (a + b) * h;
		yc = h*(b + 2*a)/(3*(a+b));
		Ic = (h*h*h*(a*a+4*a*b+b*b))/(36*(a+b));
		Ix = Ic + (h_x + yc)*(h_x + yc)*area;
		S = area * (yc + h_x);
	}
}
