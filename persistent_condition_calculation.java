package atTang;
/**
 * 
 * @Description
 * 持久状况构件应力验算
 * @author Tangel
 * @date 2022年5月16日下午1:56:38
 */
public class persistent_condition_calculation {
	
	String name;
	//正截面混凝土压应力验算
	double sigma_kc;
	//上缘
	double sigma_pt_up;
	//下缘
	double sigma_pt_down;
	
	double An;//mm^2
	double Np;//MPa * mm^2
	double epn;
	double In;
	double M_p2;
	double yn_up;
	double yn_down;
	
	double Mk;
	double y0;
	double I0;
	
	//预应力筋拉应力验算
	double sigma_pe;
	double sigma_p;
	double alpha_ep;

	//混凝土主压应力
	double sigma_pc;
	//顺序 a-a n-n o-o b-b
	double[] y = new double[4];
	//顺序 a-a n-n o-o b-b
	double[] sigma_cx = new double[4];
	//顺序 a-a n-n o-o b-b
	double[] tao = new double[4];
	double b;
	double theta;
	double V_k;
	//顺序 a-a n-n o-o b-b
	double[] So = new double[4];
	//顺序 a-a n-n o-o b-b
	double[] Sn = new double[4];
	//顺序 a-a n-n o-o b-b
	double[] sigma_cp = new double[4];
	
	private void get_sigma_pt(){
		double temp_up_1 = this.Np / this.An;
		double temp_up_2 = this.Np * this.epn * this.yn_up / this.In;
		double temp_up_3 = Math.pow(10, 6) * this.M_p2 * this.yn_up / this.In;
		this.sigma_pt_up = temp_up_1 + temp_up_2 + temp_up_3;
		
		double temp_down_1 = this.Np / this.An;
		double temp_down_2 = this.Np * this.epn * this.yn_down / this.In;
		double temp_down_3 = Math.pow(10, 6) * this.M_p2 * this.yn_down / this.In;
		this.sigma_pt_down = temp_down_1 + temp_down_2 + temp_down_3;
	}
	
	private void get_sigma_kc(){
		this.sigma_kc = Math.pow(10, 6) * this.Mk * this.y0 / this.I0;
	}
	
	//正截面压应力验算
	public void show_result_zheng(){
		System.out.println("**********正截面混凝土压应力验算************");
		System.out.println("应力部位 ：" + this.name);
		System.out.println("sigma_pt_up = " + this.sigma_pt_up);
		System.out.println("sigma_pt_down = " + this.sigma_pt_down);
		System.out.println("sigma_kc = " + this.sigma_kc + "MPa");
		System.out.println("sigma_kc + sigma_pt_up = " + (this.sigma_kc + this.sigma_pt_up));
		System.out.println("sigma_kc + sigma_pt_down = " + (this.sigma_kc + this.sigma_pt_down));
	}
	
	

	
	private void get_sigma_p(){
		double sigma_kt = Math.abs(this.Mk * this.y0 / this.I0);
		this.sigma_p = sigma_kt * this.alpha_ep;
	}
	
	public void show_result_yuyingli(){
		System.out.println("***********预应力筋拉应力验算**************");
		System.out.println("截面为" + this.name);
		System.out.println("sigma_p + sigma_pe = " + (this.sigma_p + this.sigma_pe) + "MPa");
		System.out.println("0.65fpk = 1209 MPa");
	}
	
	//
	private void get_sigma_pc(){
		double temp_sigma_1 = this.Np / this.An;
		double temp_sigma_2 = this.Np * this.epn * this.yn_down / this.In;
		double temp_sigma_3 = this.M_p2 * this.yn_down / this.In;
		
		this.sigma_pc = temp_sigma_1 + temp_sigma_2 + temp_sigma_3;
	}
	
	private void get_So(Section_of_All s){
		for(int i = 0 ;i < this.So.length;i++){
			for(int j = 0;j<s.sob.num_of_block;j++){
				if(s.sob.block[j].h_x >= s.after_yx){
					double temp_s = s.sob.block[j].area * (s.sob.block[j].yc + s.sob.block[j].h_x- s.after_yx);
					So[i] += Math.abs(temp_s);	
				}else if(s.sob.block[j].h_x < s.after_yx && (s.sob.block[j].h_x
						+ s.sob.block[j].h >= s.after_yx)){
					double temp_area = s.sob.block[j].b * (s.sob.block[j].h + s.sob.block[j].h_x- s.after_yx);
					double temp_s = temp_area * 0.5 * (s.after_yx + s.sob.block[j].h + s.sob.block[j].h_x);
					So[i] += Math.abs(temp_s);
				}
			}
		}
	}
	
	private void get_Sn(Section_of_All s){
		for(int i = 0 ;i < this.Sn.length;i++){
			for(int j = 0;j<s.sob.num_of_block;j++){
				if(s.sob.block[j].h_x >= s.before_yx){
					double temp_s = s.sob.block[j].area * (s.sob.block[j].yc + s.sob.block[j].h_x- s.before_yx);
					Sn[i] += temp_s;	
				}else if(s.sob.block[j].h_x < s.before_yx && (s.sob.block[j].h_x
						+ s.sob.block[j].h >= s.before_yx)){
					double temp_area = s.sob.block[j].b * (s.sob.block[j].h + s.sob.block[j].h_x- s.before_yx);
					double temp_s = temp_area * 0.5 * (s.before_yx + s.sob.block[j].h + s.sob.block[j].h_x);
					Sn[i] += temp_s;
				}
			}
		}
	}
	
	private void get_sigma_cx(){
		for(int i = 0;i<this.y.length;i++){
			this.sigma_cx[i] = this.sigma_pc + Math.abs(this.Mk) * this.y[i] / this.I0;
		}	
	}
	
	private void get_tao(Section_of_All s){
		for(int i = 0;i<this.tao.length;i++){
			double temp_1 = this.V_k * this.So[i] / (this.b * this.I0) ;
			double temp_2 = 0;
			for(int j = 0;j<s.sos.num_of_steal;j++){
				temp_2 += this.sigma_pe * s.sos.steal[j].area * 
						Math.sin(s.sos.steal[j].full_theta - s.sos.steal[j].theta);
			}
			double temp_3 = temp_2 * this.Sn[i] / (this.b * this.In);
			this.tao[i] = 1000 * (temp_1 - temp_3);
		}
	}
	
	private void get_sigma_cp(){
		for(int i = 0;i<this.sigma_cp.length;i++){
			double temp_1 = 0.5 * this.sigma_cx[i];
			double temp_2 = Math.pow(0.5*this.sigma_cx[i], 2) + Math.pow(this.tao[i], 2);
			this.sigma_cp[i] = temp_1 - Math.sqrt(temp_2);
		}
	}
	
	public void show_tao(){
		System.out.println("tao_a-a = " + this.tao[0]);
		System.out.println("tao_n-n = " + this.tao[1]);
		System.out.println("tao_o-o = " + this.tao[2]);
		System.out.println("tao_b-b = " + this.tao[3]);
	}
	
	public void show_sigma_cp(){
		System.out.println("sigma_tp_a-a = " + this.sigma_cp[0]);
		System.out.println("sigma_tp_n-n = " + this.sigma_cp[1]);
		System.out.println("sigma_tp_o-o = " + this.sigma_cp[2]);
		System.out.println("sigma_tp_b-b = " + this.sigma_cp[3]);
		
	}
	
	public void show_result_hunningtu(){
		System.out.println("**********混凝土主压应力验算************");
		System.out.println("计算截面：" + this.name);	
		
		System.out.printf("主应力部位 a-a ,sigma_cx(MPa) = %.2f",this.sigma_cx[0]);
		System.out.printf(",tao(MPa) = %.2f",this.tao[0]);
		System.out.printf(",sigma_cp(MPa) = %.2f",this.sigma_cp[0]);
		System.out.println();
		System.out.printf("主应力部位 n-n ,sigma_cx(MPa) = %.2f",this.sigma_cx[1]);
		System.out.printf(",tao(MPa) = %.2f",this.tao[1]);
		System.out.printf(",sigma_cp(MPa) = %.2f",this.sigma_cp[1]);
		System.out.println();
		System.out.printf("主应力部位 o-o ,sigma_cx(MPa) = %.2f",this.sigma_cx[2]);
		System.out.printf(",tao(MPa) = %.2f",this.tao[2]);
		System.out.printf(",sigma_cp(MPa) = %.2f",this.sigma_cp[2]);
		System.out.println();
		System.out.printf("主应力部位 b-b ,sigma_cx(MPa) = %.2f",this.sigma_cx[3]);
		System.out.printf(",tao(MPa) = %.2f",this.tao[3]);
		System.out.printf(",sigma_cp(MPa) = %.2f",this.sigma_cp[3]);
		System.out.println();
	}
	
	public persistent_condition_calculation(Section_of_All s, double y1 , double y2,double y3,
			double y4){
		super();
		this.name = s.name;
		this.An = s.before_area;
		this.epn = s.ep;
		this.In = s.before_I;
		this.M_p2 = s.load.M_p2;
		this.yn_down = s.before_yx;
		this.yn_up = s.sob.height - this.yn_down;
		this.Np = s.sos.area * (s.sos.steal[0].sigma_con - s.sigma_l);
		this.Mk = s.load.M_k;
		this.y0 = s.after_yx;
		this.I0 = s.after_I;
		this.alpha_ep = s.sos.steal[0].alphaep;
		this.sigma_pe = s.sos.steal[0].sigma_con - s.sigma_l;
		this.V_k = s.load.V_k;
		
		this.get_sigma_pt();
		this.get_sigma_kc();
		this.get_sigma_p();
		
		double temp_y = s.after_yx;
		this.y[0] = Math.abs(y1 - temp_y);
		this.y[1] = Math.abs(y2 - temp_y);
		this.y[2] = Math.abs(y3 - temp_y);
		this.y[3] = Math.abs(y4 - temp_y);
		this.b = s.sob.width_of_web;
		
		double temp_theta = 0;
		for(int i = 0;i < s.sos.num_of_steal;i++){
			temp_theta += s.sos.steal[i].full_theta - s.sos.steal[i].theta;
		}
		this.theta = temp_theta / s.sos.num_of_steal;
		
		this.get_sigma_pc();
		this.get_sigma_cx();
		
		this.get_So(s);
		this.get_Sn(s);
		this.get_tao(s);
		this.get_sigma_cp();
	}
}
