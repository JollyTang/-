package atTang;


public class SectionTest {
	public static void main(String[] args) {
		
		//创建一个截面
		Section_of_All s = new Section_of_All("边梁-跨中",false);
		//构造块 单位mm
		Block b1 = new Block(2075, 2075, 160, 2340);
		Block b2 = new Block(1400, 200, 90, 2250);
		Block b3 = new Block(200, 200, 1650, 600);
		Block b4 = new Block(200, 600, 250, 350);
		Block b5 = new Block(600, 600, 350, 0);
		//添加块
		s.add_element(b1);
		s.add_element(b2);
		s.add_element(b3);
		s.add_element(b4);
		s.add_element(b5);
		//确定
		s.get_base_ifo();
		//展示

		//钢束 名称，束数，张拉批次，在该位置处的角度，x,到截面下缘的距离，单根钢筋的面积，
		//有多少单根钢筋，fpd
		Steal s1 = new Steal("N1", 2, 2, Math.toRadians(7),Math.toRadians(7), 19699, 135, 139,9,1260);
		Steal s2 = new Steal("N2", 1, 1, Math.toRadians(9),Math.toRadians(9), 19696, 135, 139,8,1260);
		Steal s3 = new Steal("N3", 1, 1, Math.toRadians(8),Math.toRadians(8),19697, 250, 139,8,1260);
		Steal s4 = new Steal("N4", 1, 3, Math.toRadians(8),Math.toRadians(8), 19695, 380, 139,8,1260);
		//添加钢束
		s.add_element(s1);
		s.add_element(s2);
		s.add_element(s3);
		s.add_element(s4);
		//进行体系转换
		s.get_changed_ifo();
//		s.show_changed_ifo();

		//加荷载
//		Load load = new Load(5494.778, 1811.908, -152.986, 3424.4, -820.5, 622.1,-719.21);
//		s.add_element(load);
		Load load = new Load(5494.778, 0, 1811.908, -61.631, -152.986, -7.664, 3424.4, -820.5, -155.1, 260.4, 622.1, -31.2, -719.21, -36.03);
		s.add_element(load);
		
		//混凝土
		Concrete c = new Concrete(22.4);
		//加混凝土
		s.add_element(c);
		//求解
		s.get_all_sigma();
		//展示出来
//		s.show_all_sigma();
//		s.get_Mu();
//		s.show_Mp();
		
		
		
		//抗裂验算
		Anti_crack_test_calculation crack = new Anti_crack_test_calculation(s,
				2250,s.before_yx,s.after_yx,600);
//		正截面抗裂验算
		crack.show_result_zheng();
		crack.show_tao();
		crack.show_sigma_tp();
		
		
		//持久状况构件应力验算
//		persistent_condition_calculation p = new persistent_condition_calculation(s
//				,2250,s.before_yx,s.after_yx,600);
//		p.show_result_zheng();
//		p.show_result_yuyingli();
//		p.show_result_hunningtu();
		
		
		
		
		
		
		
		/*
		//创建一个截面
		Section_of_All s1 = new Section_of_All("边梁-支点处",true);
		
		Block b1 = new Block(2075, 2075, 160, 2340);
		Block b2 = new Block(1400, 600, 60, 2280);
		Block b3 = new Block(600, 200, 2280, 0);

		s1.add_element(b1);
		s1.add_element(b2);
		s1.add_element(b3);
		s1.get_base_ifo();
		s1.show_base_ifo();
		
		Steal st1 = new Steal("N1", 2, 2, Math.toRadians(0), 379, 850, 139,9,1260);
		Steal st2 = new Steal("N2", 1, 1, Math.toRadians(0), 355, 1230, 139,8,1260);
		Steal st3 = new Steal("N3", 1, 1, Math.toRadians(0), 377, 1730, 139,8,1260);
		Steal st4 = new Steal("N4", 1, 3, Math.toRadians(0), 352, 2080, 139,8,1260);
		
		s1.add_element(st1);
		s1.add_element(st2);
		s1.add_element(st3);
		s1.add_element(st4);
	
		Load load = new Load(0,-2445.495, -305.973, -3008.5, 507.3, 1244.2,0);
		s1.add_element(load);
		
		s1.get_changed_ifo();
		s1.get_all_sigma();
		s1.show_all_sigma();
		System.out.println(s1.sigma_l);
		*/
		
	}
}
