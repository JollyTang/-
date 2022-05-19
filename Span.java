package atTang;
/**
 * 
 * @Description
 * 该类是跨径。存放一跨中的信息
 * @author Tangel
 * @date 2022年5月14日下午2:37:37
 */
public class Span {
	//跨的名字
	String name;
	//该跨的梁数
	int num_of_beam;
	Beam[] beam = new Beam[10];
	//标准跨径
	double length_of_standard;
	//计算跨径
	double length_of_calculation;
	
	public void add_element(Beam b){
		this.beam[this.num_of_beam++] = b;
	}
}
