package atTang;
/**
 * 
 * @Description
 * 该类是截面强度验算类
 * @author Tangel
 * @date 2022年5月15日下午2:22:37
 */
public class section_strength_calculation {
	//该方法用于计算s截面的理论能承受的弯矩
	public void get_Mu(Section_of_All s){
		if(s.havereinforcement){//若受压区有预应力钢筋
			double x = this.get_x_1(s);
			if(x > s.sob.hf){
//				this.get_M_1(s);
			}
		}else{
			
		}
	}
	
	public double get_x_1(Section_of_All s){
		return 0;
	}
	

	
}
