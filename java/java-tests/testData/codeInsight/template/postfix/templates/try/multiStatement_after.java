public class Foo {
    void m() {
        try {
            somevalue<caret>
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i=0;

    }
}