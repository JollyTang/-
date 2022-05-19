package atTang;
/**
 * 
 * @Description
 * 该类是最大的类，可以认为是创建该类就是创建一个工程
 * @author Tangel
 * @date 2022年5月14日下午2:35:22
 */
public class Brideg {
	//该桥的工程名字
	String name;
	//跨数
	int num_of_span;
	//存放跨径的数组
	Span[] span = new Span[5];
	
	
	public void add_element(Span s){
		this.span[this.num_of_span++] = s;
	}
	
	
	public Brideg(String name) {
		super();
		this.name = name;
	}
	
	
	
	
}
