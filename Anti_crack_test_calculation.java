package atTang;
/**
 * 
 * @Description
 * 截面抗裂验算
 * @author Tangel
 * @date 2022年5月18日下午8:28:47
 */

public class Anti_crack_test_calculation {
	//正截面关键算
	double sigma_pc;
	double sigma_st;
	
	double ep;
	double An;
	double In;
	double I0;
	double yn;
	double y0;
	
	
	double M_of_time1;
	double M_of_time2;
	double M_car;
	double M_tem;
	double M_p2;
	
	double Np;
	//斜截面
	double M_s;
	double V_s;
	double sigma_pe;
	//顺序 a-a n-n o-o b-b
	double[] sigma_cx = new double[4];
	//顺序 a-a n-n o-o b-b
	double[] tao = new double[4];
	//顺序 a-a n-n o-o b-b
	double[] y = new double[4];
	//顺序 a-a n-n o-o b-b
	double[] So = new double[4];
	//顺序 a-a n-n o-o b-b
	double[] Sn = new double[4];
	//顺序 a-a n-n o-o b-b
	double[] sigma_tp = new double[4];
	
	//腹板厚度
	double b;
	double theta;
	
	
	
	private void get_sigma_st(){
		double temp_sigma_1 = this.M_of_time1 * this.yn / this.In;
		double temp_sigma_2 = this.M_of_time2 * this.y0 / this.I0;
		double temp_sigma_3 = 0.7 * this.M_car * this.y0 / this.I0;
		double temp_sigma_4 = 0.8 * this.M_tem * this.y0 / this.I0;
		
		this.sigma_st = temp_sigma_1 + temp_sigma_2 + temp_sigma_3 + temp_sigma_4;
		
	}
	
	private void get_sigma_pc(){
		double temp_sigma_1 = this.Np / this.An;
		double temp_sigma_2 = this.Np * this.ep * this.yn / this.In;
		double temp_sigma_3 = this.M_p2 * this.yn / this.In;
		
		this.sigma_pc = temp_sigma_1 + temp_sigma_2 + temp_sigma_3;
	}
	
	public void show_result_zheng(){
		System.out.println("sigma_st-0.85sigma_pc = " +
						(this.sigma_st - 0.85* this.sigma_pc));
	}
	
	
	//斜截面
	private void get_sigma_cx(){
		for(int i = 0;i<this.y.length;i++){
			this.sigma_cx[i] = this.sigma_pc + this.M_s * this.y[i] / this.I0;
		}	
	}
	
	private void get_tao(Section_of_All s){
		for(int i = 0;i<this.tao.length;i++){
			double temp_1 = this.V_s * this.So[i] / (this.b * this.I0) ;
			double temp_2 = 0;
			for(int j = 0;j<s.sos.num_of_steal;j++){
				temp_2 += this.sigma_pe * s.sos.steal[j].area * 
						Math.sin(s.sos.steal[j].full_theta - s.sos.steal[j].theta);
			}
			double temp_3 = temp_2 * this.Sn[i] / (this.b * this.In);
			this.tao[i] = 1000 * (temp_1 - temp_3);
		}
	}
	
	
	
	
	
	private void get_So(Section_of_All s){
		for(int i = 0 ;i < this.So.length;i++){
			for(int j = 0;j<s.sob.num_of_block;j++){
				if(s.sob.block[j].h_x >= this.y[i]){
					double temp_s = s.sob.block[j].area * (s.sob.block[j].yc + s.sob.block[j].h_x- s.after_yx);
					So[i] += Math.abs(temp_s);	
				}else if(s.sob.block[j].h_x < this.y[i] && (s.sob.block[j].h_x
						+ s.sob.block[j].h >= this.y[i])){
					double temp_area = s.sob.block[j].b * (s.sob.block[j].h + s.sob.block[j].h_x- s.after_yx);
					double temp_s = temp_area * 0.5 * (s.after_yx + s.sob.block[j].h + s.sob.block[j].h_x);
					So[i] += Math.abs(temp_s);
				}
			}
		}
	}
	
	private void get_sigma_tp(){
		for(int i = 0;i<this.sigma_tp.length;i++){
			double temp_1 = 0.5 * this.sigma_cx[i];
			double temp_2 = Math.pow(0.5*this.sigma_cx[i], 2) + Math.pow(this.tao[i], 2);
			this.sigma_tp[i] = temp_1 - Math.sqrt(temp_2);
		}
	}
	
	public void show_tao(){
		System.out.println("tao_a-a = " + this.tao[0]);
		System.out.println("tao_n-n = " + this.tao[1]);
		System.out.println("tao_o-o = " + this.tao[2]);
		System.out.println("tao_b-b = " + this.tao[3]);
	}
	
	public void show_sigma_tp(){
		System.out.println("sigma_tp_a-a = " + this.sigma_tp[0]);
		System.out.println("sigma_tp_n-n = " + this.sigma_tp[1]);
		System.out.println("sigma_tp_o-o = " + this.sigma_tp[2]);
		System.out.println("sigma_tp_b-b = " + this.sigma_tp[3]);
		
	}
	
	private void get_Sn(Section_of_All s){
		for(int i = 0 ;i < this.Sn.length;i++){
			for(int j = 0;j<s.sob.num_of_block;j++){
				if(s.sob.block[j].h_x >= this.y[i]){
					double temp_s = s.sob.block[j].area * (s.sob.block[j].yc + s.sob.block[j].h_x- s.before_yx);
					Sn[i] += temp_s;	
				}else if(s.sob.block[j].h_x < this.y[i] && (s.sob.block[j].h_x
						+ s.sob.block[j].h >= this.y[i])){
					double temp_area = s.sob.block[j].b * (s.sob.block[j].h + s.sob.block[j].h_x- s.before_yx);
					double temp_s = temp_area * 0.5 * (s.before_yx + s.sob.block[j].h + s.sob.block[j].h_x);
					Sn[i] += temp_s;
				}
			}
		}
	}
	
	
	public Anti_crack_test_calculation(Section_of_All s , double y1 , double y2,double y3,
			double y4) {
		super();
		this.M_of_time1 = s.load.M_of_time1;
		this.M_of_time2 = s.load.M_of_time2;
		this.M_car = s.load.M_car_max;
		this.M_tem = s.load.M_temperature;
		this.M_p2 = s.load.M_p2;
		this.ep = s.ep;
		
		
		this.I0 = s.after_I;
		this.y0 = s.after_yx;
		this.In = s.before_I;
		this.yn = s.before_yx;
		this.An = s.before_area;
		
		this.Np = s.sos.area * (s.sos.steal[0].sigma_con - s.sigma_l);
		
		
		this.get_sigma_st();
		this.get_sigma_pc();
		
		//斜截面
		this.M_s = Math.abs(s.load.M_short_time_max);
		this.V_s = Math.abs(s.load.V_short_time_max);
		this.sigma_pe = s.sos.steal[0].sigma_con - s.sigma_l;
		this.b = s.sob.width_of_web;
		
		double temp_y = s.after_yx;
		this.y[0] = Math.abs(y1 - temp_y);
		this.y[1] = Math.abs(y2 - temp_y);
		this.y[2] = Math.abs(y3 - temp_y);
		this.y[3] = Math.abs(y4 - temp_y);
		
		double temp_theta = 0;
		for(int i = 0;i < s.sos.num_of_steal;i++){
			temp_theta += s.sos.steal[i].full_theta - s.sos.steal[i].theta;
		}
		this.theta = temp_theta / s.sos.num_of_steal;
		
		this.get_So(s);
		this.get_Sn(s);
		
		
		
		this.get_sigma_cx();
		this.get_tao(s);
		this.get_sigma_tp();
	}
	
	
	
}

