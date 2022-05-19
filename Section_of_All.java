package atTang;
/**
 * 
 * @Description
 * 该类是所有section类的总和
 * @author Tangel
 * @date 2022年5月11日下午2:39:19
 */
public class Section_of_All {
	//截面的名称
	String name;
	//受压区受否具有钢筋
	boolean havereinforcement;
	//截面的块属性
	Section_of_Block sob = new Section_of_Block();
	//截面的预应力钢筋属性
	Section_of_Steal sos = new Section_of_Steal();
	//截面荷载属性
	Load load;
	
	//净截面特性
	double before_area;
	double before_I;
	double before_yx;
	
	//转换后的截面特性
	double after_area;
	double after_I;
	double after_yx;
	//预应力筋至截面重心轴的距离
	double ep;
	//预应力6会用到的
    double rou;
    double rou_ps;
    double sigma_pc_6;
	//预应力损失 该截面的平均预应力损失 各个钢塑上的预应力损失在steal类上
	double sigma_l1;
	double sigma_l2;
	double sigma_l4;
	double sigma_l5;
	double sigma_l6;

	double sigma_1;
	double sigma_2;
	double sigma_l;
	//
	double Mp;
	
	
	
	
	
	
	//在添加完块后这个操作可以使截面获得净截面的截面特性
	public void get_base_ifo(){
		System.out.println("*********成功获得净截面特性************");
		this.sob.finish_add_block();
		this.before_area = sob.area;
		this.before_I = sob.Ic;
		this.before_yx = sob.yx;
	}
	
	public void show_base_ifo(){
		System.out.println("************净截面特性******************");
		System.out.println("该截面的名称 ：" + this.name);
		System.out.println("面积 = " + this.before_area + "mm^2");
		System.out.println("惯性矩 = " + this.before_I + "mm^4");
		System.out.println("重心轴至截面下缘的距离 = " + this.before_yx + "mm");
		System.out.println("*******************************");
	}
	
	//获取截面转换后的截面特性
	public void get_changed_ifo(){
		System.out.println("*********成功获得变换后的截面特性************");
		this.sos.finish_add_steal();
		double temp_area = this.sos.area * 4.797;
		this.after_area = temp_area + this.before_area;
		//距离上端的距离
		double temp_ys_of_block = this.sob.height - this.sob.yx;
		double temp_ys_of_steal = this.sob.height - this.sos.yx;

		//距离上端的面积矩
		double temp_s_of_block = temp_ys_of_block * this.sob.area;
		double temp_s_of_steal = temp_ys_of_steal * temp_area;

		double temp_ys = (temp_s_of_block + temp_s_of_steal)/this.after_area;
		//截面转换后的距离下端的距离
		this.after_yx = this.sob.height - temp_ys;

		
		double temp_delta_y_of_block = temp_ys_of_block - temp_ys;
		double temp_delta_y_of_steal = temp_ys_of_steal - temp_ys;
		
		double temp_delta_I_of_block = this.sob.area  * Math.pow(temp_delta_y_of_block, 2);
		double temp_delta_I_of_steal = temp_area  * Math.pow(temp_delta_y_of_steal, 2);
		
		this.after_I = this.sob.Ic + temp_delta_I_of_block + temp_delta_I_of_steal;
		
		this.ep = Math.abs(this.sos.yx - this.after_yx);
	}
	
	public void show_changed_ifo(){
		System.out.println("************转换后的截面特性*******************");
		System.out.println("该截面的名称 ：" + this.name);
		System.out.println("面积 = " + this.after_area + "mm^2");
		System.out.println("惯性矩 = " + this.after_I + "mm^4");
		System.out.println("重心轴至截面下缘的距离 = " + this.after_yx + "mm");
		System.out.println("*******************************");
	}
	
	//计算预应力损失
	public void get_sigma_l1(){
	    double temp_sigma_l1 = 0;
	    //for循环中的计算是遍历每一根钢筋，算其预应力损失
	    for(int i = 0;i<sos.num_of_steal;i++){
	    	double temp1 = this.sos.steal[i].mu * this.sos.steal[i].theta;
	    	double temp2 = this.sos.steal[i].k * this.sos.steal[i].x* 0.001;
	    	this.sos.steal[i].beta = 1 - Math.pow(Math.E,-(temp1 + temp2));
	    	this.sos.steal[i].sigma_l1 = this.sos.steal[i].sigma_con * this.sos.steal[i].beta;
	    	temp_sigma_l1 += this.sos.steal[i].sigma_l1;
	    }
	    //这一代码是算整个截面的平均预应力损失
	    this.sigma_l1 = temp_sigma_l1 / this.sos.num_of_steal;    
	}
	
	public void show_sigma_l1(){
		System.out.println("*******预应力损失1***********");
		System.out.println("钢束编号\tθ\tμθ\tx(m)\tkx\tβ\tσcon(MPa)\tσl1(MPa)");
		for(int i = 0;i<this.sos.num_of_steal;i++){
			this.sos.steal[i].show_sigma_l1();
		}
		System.out.println("该截面的平均预应力损失1为" + this.sigma_l1 + "MPa");	
		System.out.println("******************");
	}
	
	public void get_sigma_l2(){
		double temp_sigma_l2 = 0;
		for(int i = 0;i<this.sos.num_of_steal;i++){
	    	if(this.sigma_l1 == 0){
	    		System.out.println("请先计算sigma_l1");
	    	}
	    	double sigma_l = this.sos.steal[i].sigma_con - this.sos.steal[i].sigma_l1;
	    	this.sos.steal[i].delta_sigma_d = (this.sos.steal[i].sigma_con - sigma_l) / this.sos.steal[i].x;
	    	double temp = this.sos.steal[i].sigma_delta_l * this.sos.steal[i].Ep / this.sos.steal[i].delta_sigma_d;
	    	this.sos.steal[i].lf = Math.sqrt(temp);
	    	if(this.sos.steal[i].lf > this.sos.steal[i].x){
	    		this.sos.steal[i].sigma_l2 = 2 * this.sos.steal[i].delta_sigma_d * (this.sos.steal[i].lf - this.sos.steal[i].x);
	    	}
	    	temp_sigma_l2 += this.sos.steal[i].sigma_l2;
	    	this.sigma_l2 = temp_sigma_l2 / this.sos.num_of_steal;
		}
	}

	public void show_sigma_l2(){
		System.out.println("*******预应力损失2***********");
		System.out.printf("钢束\t");
		System.out.printf("束数\t");
		System.out.printf("x(m)\t");
		System.out.printf("l(m)\t");
		System.out.printf("Δσd(MPa/m)\t");
		System.out.printf("ΣΔl(m)\t");
		System.out.printf("Ep(MPa)\t");
		System.out.printf("        lf(m)\t");
		System.out.printf("σl2(MPa)\t");
		System.out.println();
		
		for(int i = 0;i<this.sos.num_of_steal;i++){
			this.sos.steal[i].show_sigma_l2();
		}
		System.out.println("该截面的平均预应力损失2为" + this.sigma_l2 + "MPa");	
		System.out.println("******************");
	}
	
	public void get_sigma_l4(){
		double temp_sigma_l4 = 0;
		for(int i = 0;i<this.sos.num_of_steal;i++){
			double temp_m = this.sos.m_max - this.sos.steal[i].m;
			double sigma_pe = this.sos.steal[i].sigma_con - this.sos.steal[i].sigma_l1 
					- this.sos.steal[i].sigma_l2;
			this.sos.steal[i].Np = sigma_pe * this.sos.steal[i].area;
			this.sos.steal[i].sigma_pc_4 = (this.sos.steal[i].Np / this.after_area + 
					(this.sos.steal[i].Np * this.ep*this.ep) / this.after_I)/temp_m;
			
			this.sos.steal[i].sigma_l4 = 0.5 * this.sos.steal[i].sigma_pc_4 * 
					this.sos.steal[i].alphaep * (temp_m-1);
			
			temp_sigma_l4 += this.sos.steal[i].sigma_l4;
		}
		this.sigma_l4 = temp_sigma_l4 / this.sos.num_of_steal;
	}
	
	public void show_sigma_l4(){
		System.out.println("*******预应力损失4***********");
		System.out.printf("束号\t");
		System.out.printf("m-1\t");
		System.out.printf("simga_l1(MPa)\t");
		System.out.printf("simga_l2(MPa)\t");
		System.out.printf("Ap(m^2)\t");
		System.out.printf("        Np(Kn)\t");
		System.out.printf("        simga_pc(MPa)\t");
		System.out.printf("sigma_l4\t");
		System.out.println();
		
		for(int i = 0;i<this.sos.num_of_steal;i++){
			this.sos.steal[i].show_sigma_l4();
		}
		
		System.out.println("该截面的平均预应力损失4为" + this.sigma_l4 + "MPa");	
		System.out.println("******************");
	}
	
	public void get_sigma_l5(){
		double temp_sigma_l5 = 0;
		for(int i = 0;i<this.sos.num_of_steal;i++){
			this.sos.steal[i].sigma_pc_5 = this.sos.steal[i].sigma_con - 
					this.sos.steal[i].sigma_l1 - this.sos.steal[i].sigma_l2 - 
					this.sos.steal[i].sigma_l4;
			this.sos.steal[i].sigma_l5 = this.sos.steal[i].fai * this.sos.steal[i].zeta * 
					this.sos.steal[i].sigma_pc_5 * 
					(0.52 * this.sos.steal[i].sigma_pc_5 / this.sos.steal[i].fpk - 0.26);
			temp_sigma_l5 += this.sos.steal[i].sigma_l5;
		}
		this.sigma_l5 = temp_sigma_l5 / this.sos.num_of_steal;
	}
	
	public void show_sigma_l5(){
		System.out.println("*******预应力损失5***********");
		System.out.printf("束号\t");
		System.out.printf("sigma_l1(MPa)\t");
		System.out.printf("sigma_l2(MPa)\t");
		System.out.printf("sigma_l4(MPa)\t");
		System.out.printf("sigma_pc(MPa)\t");
		System.out.printf("sigma_l5(MPa)\t");
		System.out.println();
		
		for(int i = 0;i<this.sos.num_of_steal;i++){
			this.sos.steal[i].show_sigma_l5();
		}
		
		System.out.println("该截面的平均预应力损失5为" + this.sigma_l5 + "MPa");	
		System.out.println("******************");
	}
	
	public void get_sigma_l6(){
		double temp_sigma_l6 = 0;
		double temp_sigma_pc_6 = 0;
		for(int i = 0;i<this.sos.num_of_steal;i++){
			double temp_fai = 2.49;
			double temp_si = 0.25*Math.pow(10, -3);
							
			this.rou = this.sos.area / this.before_area;
			double i_2 = this.before_I / this.before_area;
			this.rou_ps = 1 + Math.pow(this.ep, 2) / i_2;
			
			double N_pI = (this.sos.steal[i].sigma_con - 
					this.sos.steal[i].sigma_l1 - this.sos.steal[i].sigma_l2
					- this.sos.steal[i].sigma_l4) * this.sos.area;
			
			double temp_1 = N_pI / this.after_area;
			double temp_2 = N_pI * this.ep / this.after_I;
			double Wnp = this.after_I / this.after_yx;
			double Wop = this.before_I / this.before_yx;
			double temp_3 = this.load.M_of_time1 / Wnp;
			double temp_4 = (1.25 / 1.69) * (this.load.M_of_time2 / Wop) ; 
	
			this.sos.steal[i].sigma_pc_6 = temp_1 + temp_2 -temp_3 - temp_4;
			
			double up = 0.9 * (this.sos.steal[i].Ep * temp_si + this.sos.steal[i].alphaep
					* this.sos.steal[i].sigma_pc_6 * temp_fai);
			double down = 1 + 15 * this.rou * this.rou_ps;
			this.sos.steal[i].sigma_l6 = up / down;
			
			temp_sigma_pc_6 += this.sos.steal[i].sigma_pc_6;
			temp_sigma_l6 += this.sos.steal[i].sigma_l6;
		}
		this.sigma_pc_6 = temp_sigma_pc_6 / this.sos.num_of_steal;
		this.sigma_l6 = temp_sigma_l6 / this.sos.num_of_steal;
	}
	
	public void show_sigma_l6(){
		System.out.println("*******预应力损失6***********");
		System.out.printf("截面\t");
		System.out.printf("        rou\t");
		System.out.printf("rou_ps\t");
		System.out.printf("sigma_pc(MPa)\t");
		System.out.printf("sigma_l6(MPa)\t");
		System.out.println();
		
		System.out.printf("%s\t",this.name);
		System.out.printf("%.4f\t",this.rou);
		System.out.printf("%.4f\t",this.rou_ps);
		System.out.printf("%.4f\t",this.sigma_pc_6);
		System.out.printf("        %.4f\t",this.sigma_l6);
		System.out.println();
		
		System.out.println("******************");
	}
	
	public void get_all_sigma(){
		this.get_sigma_l1();
		this.get_sigma_l2();
		this.get_sigma_l4();
		this.get_sigma_l5();
		this.get_sigma_l6();
		this.sigma_1 = this.sigma_l1 + this.sigma_l2 + this.sigma_l4;
		this.sigma_2 = this.sigma_l5 + this.sigma_l6;
		this.sigma_l = this.sigma_1 + this.sigma_2;	
	}
	
	
	public void show_all_sigma(){
		this.show_sigma_l1();
		this.show_sigma_l2();
		this.show_sigma_l4();
		this.show_sigma_l5();
		this.show_sigma_l6();
	}
	
	
	public void add_element(Block b){
		this.sob.add_block(b);
	}
	
	public void add_element(Load l){
		this.load = l;
	}
	
	
	public void add_element(Steal s){
		this.sos.add_Steal(s);
	}
	
	
	

	public void add_element(Concrete c){
		this.sob.add_concrete(c);
	}
	

	
	
	//受压区不配置钢筋的截面
	private void get_x(){
		double temp_F = 0;
		for(int i = 0;i<this.sos.num_of_steal;i++){
			temp_F += this.sos.steal[i].fpd * this.sos.steal[i].area;
		}
		this.sob.x = temp_F / (this.sob.concrete.fcd * this.sob.width_of_wing_edges);
	}
	//求解第一类型的弯矩
	private void get_M_1(){
		double temp_h0 = this.sob.height - this.sos.yx;
		double x = this.sob.x;
		double M_1 = this.sob.concrete.fcd * this.sob.width_of_wing_edges * x
				* (temp_h0 - 0.5 * x);
		this.Mp = M_1;
	}
	
	public void show_Mp(){
		System.out.println("****************************");
		System.out.println("该截面为" + this.name);
		if(this.sob.x < this.sob.hf){
			System.out.printf("该截面受压区高度为%.0fmm\n",this.sob.x);
			System.out.printf("该截面翼缘板厚度为%.0fmm\n",this.sob.hf);
			System.out.println("该截面为第一类T型截面");
		}else{
			System.out.printf("该截面受压区高度为%.0fmm\n", this.sob.x);
			System.out.printf("该截面翼缘板厚度为%.0fmm\n", this.sob.hf);
			System.out.println("该截面为第二类T型截面");
		}
		System.out.printf("Mu = %.3f kN·m", this.Mp*Math.pow(10, -6));
		System.out.println();
	}
	//求解第二类型的弯矩
	private void get_M_2(){
		System.out.println("这个方程还没写");
	}
	//受压区不配置钢筋
	public void get_Mu(){
		this.get_x();
		double temp_x = this.sob.x; 
		if(temp_x < this.sob.hf){
			this.get_M_1();
		}else{
			this.get_M_2();
		}
	}
	//算极限状态下的预应力钢筋的根数
	public void get_num_of_steal(){
		//这里的M得自己设
		double M = 14182.442;
		double Ay = this.sos.area / this.sos.num_of_singel_steal;
		double temp_1 = (this.sob.concrete.fcd * this.sob.width_of_wing_edges )/
				(Ay * this.sos.steal[0].fpd); 
		double temp_h0 = this.sob.height - this.sos.yx;
		double temp_3 = Math.pow(temp_h0, 2) - (2 * M*1000)/(this.sob.width_of_wing_edges *0.001* this.sob.concrete.fcd);
		double temp_2 = temp_h0 - Math.sqrt(temp_3);
		double ny = temp_1 * temp_2;
		System.out.println("ny = " + ny);
	}
	
	
	
	
	
	
	public Section_of_All(String name , boolean b) {
		super();
		this.name = name;
		this.havereinforcement = b;
	}
	
	
}
