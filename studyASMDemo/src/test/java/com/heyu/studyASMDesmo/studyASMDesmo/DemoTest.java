package com.heyu.studyASMDesmo.studyASMDesmo;

import org.junit.Test;
import org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DemoTest extends ClassLoader{

	@Test
	public void demoTest() throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {
		ClassReader cr = new ClassReader(HelloWorld.class.getName());
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		CustomVistor cv = new CustomVistor(Opcodes.ASM4,cw);
		cr.accept(cv,0);
		byte[] code = cw.toByteArray();
		DemoTest loader = new DemoTest();
		Class clazz = loader.defineClass(null, code, 0, code.length);
		clazz.getMethods()[0].invoke(clazz.newInstance(),new Object[]{});
	}


}
class CustomVistor extends ClassVisitor implements Opcodes{
	public CustomVistor(int api, ClassVisitor cv){
		super(api, cv);
	}

	@Override
	public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
		MethodVisitor mv = super.visitMethod(i,s,s1,s2,strings);
		if(s.equals("sayHello")){
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V");
		}
		return mv;
	}
}

