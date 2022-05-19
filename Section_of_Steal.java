package atTang;
/**
 * 
 * @Description
 * 该类是钢束的总和 
 * @author Tangel
 * @date 2022年5月11日下午2:52:49
 */

public class Section_of_Steal extends Surface{
	//钢束的数量
	int num_of_steal;
	//钢筋的数量
	int num_of_singel_steal;
	//钢束的数组
	Steal[] steal = new Steal[10];
	//张拉的最大次数
	double m_max;
	
	
	
	
	
	public void add_Steal(Steal s){
		this.steal[num_of_steal++] = s;
	}
	
	public void del_steal(int num){
		for(int i = num - 1;i < this.num_of_steal - 1;i++){
			steal[i] = steal[i + 1];
		}
		this.num_of_steal--;
	}
	
	private void set_area(){
		this.area = 0;
		for(int i = 0;i<this.num_of_steal;i++){
			this.area += steal[i].area * steal[i].num;
		}
		System.out.println("1.设置面积完成");
	}
	
	private void set_Sx(){
		
		for(int i = 0;i<this.num_of_steal;i++){
			this.Sx += steal[i].S;
		}
		System.out.println("2.设置Sx完成");
	}
	
	private void set_yx(){
		if(this.area == 0){
			System.out.println("没有面积");
		}else{
			this.yx = this.Sx / this.area;
			System.out.println("3.设置yx完成");	
		}
	}
	
	private void get_m(){
		for(int i = 0;i < this.num_of_steal;i++){
			if(this.m_max < this.steal[i].m){
				this.m_max = this.steal[i].m;
			}
		}
		this.m_max += 1;
		
		for(int i = 0;i < this.num_of_steal;i++){
			this.steal[i].m_1 = (int) (this.m_max - this.steal[i].m - 1);
		}
	}
	
	private void get_num_of_singel_steal(){
		for(int i = 0;i<this.num_of_steal;i++){
			this.num_of_singel_steal += this.steal[i].num_of_singel_steel
					* this.steal[i].num;
		}
	}
	
	
	
	
	public void finish_add_steal(){
		set_area();
		set_Sx();
		set_yx();
		get_m();
		get_num_of_singel_steal();
	}
}
