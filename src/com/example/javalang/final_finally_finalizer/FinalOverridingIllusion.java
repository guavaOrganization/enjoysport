package com.example.javalang.final_finally_finalizer;

public class FinalOverridingIllusion {
	public static void main(String[] args) {
		OverridingPrivate2 op2 = new OverridingPrivate2();
		op2.f();
		op2.g();

		OverridingPrivate op = op2;
		// op.f();
		// op.g();

		WithFinals wf = op2;
		// wf.f();
		// wf.g();
	}
}
