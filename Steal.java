package atTang;
/**
 * 
 * @Description
 * 记录预应力筋的一些信息 and 一些中间变量
 * @author Tangel
 * @date 2022年5月11日下午8:09:13
 */
public class Steal {
    //String类 记录钢束的名字
    String name;
    //int类，记录该束的数目
    int num;
    //double类，记录该钢束在该位置的角度θ
    double theta;
    //记录钢束偏转的角度
    double full_theta;
    //double类，记录该钢束在该位置的x
    double x;
    //double 记录钢束距离梁底的距离
    double yx;
    //double 记录钢束的面积
    double area;
    //单根钢筋的数量
    int num_of_singel_steel;
    //单根钢筋的面积
    double area_of_singel;
    //doubel 记录钢束面积矩
    double S;
    //预应力钢束的强度
    double fpd;
    //
    
    //double类，记录预应力损失1
    double sigma_l1 = 0;
    
	//预应力损失1中的一些中间变量
    double mu = 0.25;
    double k = 0.0015;
    double sigma_con = 1395;
    double beta = 0;
    
    //double类, 预应力损失2
    double sigma_l2 = 0;
    
    //预应力损失2中的一些中间变量
    double Ep = 195000;
    double sigma_delta_l = 6; //单位mm
    double lf = 0;
    double delta_sigma_d = 0;
    
    //double类, 预应力损失4
    double sigma_l4 = 0;
    //预应力损失4中的一些中间变量
    double alphaep = 5.65;//预应力钢筋弹性模量与混凝土的弹性模量的比值
    double Np;
    int m;//张拉的批次
    int m_1;
    double sigma_pc_4;
    
    //double类, 预应力损失5
    double sigma_l5 = 0;
    
    //预应力损失5中会用到的值
    double fai = 0.9;//张拉系数
    double zeta = 0.3;//钢筋松弛系数
    double fpk = 1860;
    double sigma_pc_5;
    //double类, 预应力损失6
    double sigma_l6 = 0;
    //预应力损失6中会用到的值

    double sigma_pc_6;
    
    
    
	public Steal(String name, int num,int m, double theta, double full_theta, double x, double yx, double area,int num_of_sigel_steal, double fpd) {
		super();
		this.name = name;
		this.num = num;
		this.m = m;
		this.theta = theta;
		this.full_theta = full_theta;
		this.x = x;
		this.yx = yx;
		this.area_of_singel = area;
		this.area = this.area_of_singel * num_of_sigel_steal;
		this.num_of_singel_steel = num_of_sigel_steal;
		this.fpd = fpd;
				
		this.S = this.area * this.yx;
	}   
	

    public void show_sigma_l1(){
    	System.out.printf("%s\t",this.name);
    	System.out.printf("%.4f\t",this.theta);
    	System.out.printf("%.4f\t",this.theta*this.mu);
    	System.out.printf("%.4f\t",this.x*0.001);
    	System.out.printf("%.4f\t",this.x * k * 0.001);
    	System.out.printf("%.4f\t",this.beta);
    	System.out.printf("%.4f\t",this.sigma_con);
    	System.out.printf("%.4f\t",this.sigma_l1);
    	System.out.println();
    }
    
    public void show_sigma_l2(){
		System.out.printf("%s\t",this.name);
		System.out.printf("%d\t",this.num);
		System.out.printf("%.1f\t",this.x*0.001);
		System.out.printf("38.62");
		System.out.printf("   %.4f  \t",this.delta_sigma_d);
		System.out.printf("%.3f\t",this.sigma_delta_l*0.001);
		System.out.printf("%.1f\t",this.Ep);
		System.out.printf("%.2f\t",this.lf*0.001);
		System.out.printf("%.4f\t",this.sigma_l2);
		System.out.println();
    }
    
    public void show_sigma_l4(){
		System.out.printf("%s\t",this.name);
		System.out.printf("%d\t",this.m_1);
		System.out.printf("%.1f\t ",this.sigma_l1);
		System.out.printf("       %.1f\t",this.sigma_l2);
		System.out.printf("        %.3f\t",this.area);
		System.out.printf("%.3f\t",this.Np);
		System.out.printf("%.3f\t",this.sigma_pc_4);
		System.out.printf("        %.1f\t",this.sigma_l4);
		System.out.println();
    }
    
    public void show_sigma_l5(){
		System.out.printf("%s\t",this.name);
		System.out.printf("%.1f\t",this.sigma_l1);
		System.out.printf("        %.1f\t",this.sigma_l2);
		System.out.printf("        %.1f\t",this.sigma_l4);
		System.out.printf("        %.1f\t",this.sigma_pc_5);
		System.out.printf("        %.1f\t",this.sigma_l5);
		System.out.println();
    }
    
    public void show_sigma_l6(){
    	
    }
}
