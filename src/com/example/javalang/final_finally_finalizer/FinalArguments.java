package com.example.javalang.final_finally_finalizer;

public class FinalArguments {
	void with(final Gizmo g) {
		// g = new Gizmo();
	}

	void without(Gizmo g) {
		g = new Gizmo(2);
		g.spin();
	}

	void f(final int i) {
		// i++; // 不能修改参数
	}

	int g(final int i) {
		return i + 1;
	}
	
	public static void main(String[] args) {
		FinalArguments fa = new FinalArguments();
		Gizmo gizmo = null;
		fa.with(gizmo);
		fa.without(gizmo);
		gizmo = new Gizmo(3);
		gizmo.spin();
	}
}
