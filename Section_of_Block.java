package atTang;
/**
 * 
 * @Description
 * 该类是块类的集合，
 * @author Tangel
 * @date 2022年5月11日下午2:51:39
 */


public class Section_of_Block extends Surface{
	//属性
	//用于存放数组
	Block[] block = new Block[10];
	//数组中的块数
	int num_of_block;
	//整个截面的高，或者说梁高
	double height;
	//混凝土强度
	Concrete concrete;
	//翼缘板宽度
	double width_of_wing_edges;
	//翼缘板厚度
	double hf;
	//受压区高度X
	double x;
	//腹板宽度
	double width_of_web;
	
	
	//构造器
	public Section_of_Block() {
		super();
	}
	
	//方法
	public void add_block(Block B){
		this.block[num_of_block++] = B;
	}
	


	public void dele_block(int num){
		for(int i = num - 1;i<num_of_block - 1;i++){
			block[i] = block[i + 1];
		}
		this.num_of_block--;
	}
	
	public void add_concrete(Concrete c){
		this.concrete = c;
	}
	
	private void set_area(){
		this.area = 0;
		for(int i = 0;i<num_of_block;i++){
			this.area += block[i].area;
		}
		System.out.println("1.设置面积完成");
	}
	
	private void set_Ix(){
		this.Ix = 0;
		for(int i = 0;i<num_of_block;i++){
			this.Ix += block[i].Ix;
		}
		System.out.println("2.设置Ix完成");
	}
	
	private void set_Sx(){
		Sx = 0;
		for(int i = 0;i<num_of_block;i++){
			this.Sx += block[i].S;
		}
		System.out.println("3.设置Sx完成");
	}
	
	private void set_yx(){
		if(this.area == 0){
			System.out.println("没有面积");
		}else{
			this.yx = this.Sx / this.area;
			System.out.println("4.设置yx完成");	
		}
	}
	

	
	private void set_Ic(){
		this.Ic = this.Ix - this.yx*this.yx*this.area;
		System.out.println("5.设置Ic完成");
	}
	
	private void set_height(){
		for(int i = 0;i<this.num_of_block;i++){
			this.height += block[i].h;
		}
	}
	//近似计算，取所有块中最宽的那个。
	private void set_width_of_wing_edges(){
		double temp_width = 0; 
		for(int i = 0; i < this.num_of_block;i++){
			if(this.block[i].a > temp_width){
				temp_width = this.block[i].a;
			}
		}
		this.width_of_wing_edges = temp_width;
	}
	//近似取第一块的高
	private void set_thickness_of_wing_edges(){
		this.hf = this.block[0].h;
	}
	
	private void set_width_of_web(){
		double temp = 0;
		for(int i = 0;i<this.num_of_block;i++){
			if(this.block[i].h > temp && this.block[i].a == this.block[i].b){
				temp = this.block[i].h;
				this.width_of_web = this.block[i].b;
			}
		}
	}
	
	public void finish_add_block(){
		set_area();
		set_Ix();
		set_Sx();
		set_yx();
		set_Ic();
		set_height();
		set_width_of_wing_edges();
		set_thickness_of_wing_edges();
		set_width_of_web();
	}
}
