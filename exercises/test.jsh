interface I {
}

class A implements I {
}

I i1 = new I();
I i2 = new A();
A a1 = i2;
A a2 = (A)i2;
