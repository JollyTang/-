package atTang;

public class Load {
	//一期荷载
	double M_of_time1;
	double V_of_time1;
	//二期荷载  一期之外的其他都包含在二期中
	double M_of_time2;
	double V_of_time2;
	//结构自重
	double M_self_weight;
	double V_self_weight;
	//基础沉降
	double M_sedimentation;
	double V_sedimentation;
	//汽车荷载
	double M_car_max;
	double M_car_min;
	
	double V_car_max;
	double V_car_min;
	//温度效应
	double M_temperature;
	double V_temperature;
	//配束后的次弯矩
	double M_p2;
	double V_p2;

	//承载能力极限状态
	double M_limit_max;
	double M_limit_min;
	
	double V_limit_max;
	double V_limit_min;
	
	//短期
	double M_short_time_max;
	double M_short_time_min;
	
	double V_short_time_max;
	double V_short_time_min;
	//长期
	double M_long_time_max;
	double M_long_time_min;
	
	double V_long_time_max;
	double V_long_time_min;
	//标准值
	double M_k;
	double V_k;
	
	public Load(double m_of_time1, double m_of_time2, double m_sedimentation, double m_car_max, double m_car_min,
			double m_temperature,double m_p2) {
		super();
		this.M_of_time1 = m_of_time1;
		this.M_of_time2 = m_of_time2;
		this.M_sedimentation = m_sedimentation;
		this.M_car_max = m_car_max;
		this.M_car_min = m_car_min;
		this.M_temperature = m_temperature;
		this.M_p2 = m_p2;
		
		this.M_self_weight = this.M_of_time1 + this.M_of_time2;
		//极限
		this.M_limit_max= 1.2 * this.M_self_weight + 0.5 * this.M_sedimentation
				+ 1.4 * this.M_car_max + 0.8*1.4*this.M_temperature;
		this.M_limit_min = 1.2 * this.M_self_weight + 0.5 * this.M_sedimentation
				+ 1.4 * this.M_car_min + 0.8*1.4*this.M_temperature;
		//短期
		this.M_short_time_max= this.M_self_weight + this.M_sedimentation
				+ 0.7 * this.M_car_max + 0.8*this.M_temperature;
		this.M_short_time_min = this.M_self_weight + this.M_sedimentation
				+ 0.7 * this.M_car_min + 0.8*this.M_temperature;
		//长期
		this.M_long_time_max= this.M_self_weight + this.M_sedimentation
				+ 0.4 * this.M_car_max + 0.8*this.M_temperature;
		this.M_long_time_min= this.M_self_weight + this.M_sedimentation
				+ 0.4 * this.M_car_min + 0.8*this.M_temperature;
		
		
	}

	public Load(double m_of_time1, double v_of_time1, double m_of_time2, double v_of_time2, double m_sedimentation,
			double v_sedimentation, double m_car_max, double m_car_min, double v_car_max, double v_car_min,
			double m_temperature, double v_temperature, double m_p2, double v_p2) {
		super();
		M_of_time1 = m_of_time1;
		V_of_time1 = v_of_time1;
		M_of_time2 = m_of_time2;
		V_of_time2 = v_of_time2;
		M_sedimentation = m_sedimentation;
		V_sedimentation = v_sedimentation;
		M_car_max = m_car_max;
		M_car_min = m_car_min;
		V_car_max = v_car_max;
		V_car_min = v_car_min;
		M_temperature = m_temperature;
		V_temperature = v_temperature;
		M_p2 = m_p2;
		V_p2 = v_p2;
		//弯矩
		this.M_self_weight = this.M_of_time1 + this.M_of_time2;
		//极限
		this.M_limit_max= 1.2 * this.M_self_weight + 0.5 * this.M_sedimentation
				+ 1.4 * this.M_car_max + 0.8*1.4*this.M_temperature;
		this.M_limit_min = 1.2 * this.M_self_weight + 0.5 * this.M_sedimentation
				+ 1.4 * this.M_car_min + 0.8*1.4*this.M_temperature;
		//短期
		this.M_short_time_max= this.M_self_weight + this.M_sedimentation
				+ 0.7 * this.M_car_max + 0.8*this.M_temperature;
		this.M_short_time_min = this.M_self_weight + this.M_sedimentation
				+ 0.7 * this.M_car_min + 0.8*this.M_temperature;
		//长期
		this.M_long_time_max= this.M_self_weight + this.M_sedimentation
				+ 0.4 * this.M_car_max + 0.8*this.M_temperature;
		this.M_long_time_min= this.M_self_weight + this.M_sedimentation
				+ 0.4 * this.M_car_min + 0.8*this.M_temperature;
		//剪力
		this.V_self_weight = this.V_of_time1 + this.V_of_time2;
		
		//极限
		this.V_limit_max= 1.2 * this.V_self_weight + 0.5 * this.V_sedimentation
				+ 1.4 * this.V_car_max + 0.8*1.4*this.V_temperature;
		this.V_limit_min = 1.2 * this.V_self_weight + 0.5 * this.V_sedimentation
				+ 1.4 * this.V_car_min + 0.8*1.4*this.V_temperature;
		//短期
		this.V_short_time_max= this.V_self_weight + this.V_sedimentation
				+ 0.7 * this.V_car_max + 0.8*this.V_temperature;
		this.V_short_time_min = this.V_self_weight + this.V_sedimentation
				+ 0.7 * this.V_car_min + 0.8*this.V_temperature;
		//长期
		this.V_long_time_max= this.V_self_weight + this.V_sedimentation
				+ 0.4 * this.V_car_max + 0.8*this.V_temperature;
		this.V_long_time_min= this.V_self_weight + this.V_sedimentation
				+ 0.4 * this.V_car_min + 0.8*this.V_temperature;
		
		//标准值
		this.M_k = this.M_self_weight + this.M_sedimentation + this.M_car_max
				+ this.M_temperature + this.M_p2;
		this.V_k = this.V_self_weight + this.V_sedimentation + this.V_car_max
				+ this.V_temperature + this.V_p2;
	}

		
}
