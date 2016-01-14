import grid.Grid;
import grid.DenseGrid;
import grid.SparseGrid;
import draughts.Piece;
import draughts.Man;
import draughts.King;
import draughts.InvalidStepException;
import position.Position;
import position.Direction;
import java.util.*;

class Test {
    public static void main(String[] args) {
        System.out.print("Direction tesztelese... ");
        directionTest();
        System.out.println("kesz");
        System.out.print("Position tesztelese... ");
        positionTest();
        System.out.println("kesz");
        System.out.print("DenseGrid es AbstractGrid tesztelese... ");
        denseGridTest();
        System.out.println("kesz");
        System.out.print("SparseGrid tesztelese... ");
        sparseGridTest();
        System.out.println("kesz");
        System.out.print("Man es Piece tesztelese... ");
        manTest();
        System.out.println("kesz");
        System.out.print("King tesztelese... ");
        kingTest();
        System.out.println("kesz");
    }

    static void directionTest() {
        int[] up_left_dist, up_right_dist, down_left_dist, down_right_dist;
        down_left_dist = new int[] {-10, -34};
        down_right_dist = new int[] {41, -55};
        up_left_dist = new int[] {-13, 6};
        up_right_dist = new int[] {42, 9};

        String msg = "Direction.fromDirection nem jo iranyt ad vissza";
        assertEquals(msg, Direction.UP_LEFT, Direction.fromDistance(up_left_dist));
        assertEquals(msg, Direction.UP_RIGHT, Direction.fromDistance(up_right_dist));
        assertEquals(msg, Direction.DOWN_LEFT, Direction.fromDistance(down_left_dist));
        assertEquals(msg, Direction.DOWN_RIGHT, Direction.fromDistance(down_right_dist));

    }

    static void positionTest() {
        // Konstruktor
        int h = 52, v = 33;
        Position p = new Position(h, v);
        assertEquals("A Position konstruktora nem jol inicializalja a vizszintes koordinatat", h, p.h);
        assertEquals("A Position konstruktora nem jol inicializalja a fuggoleges koordinatat", v, p.v);

        // toString
        assertEquals("A Postion.toString eredmenye nem jo alaku", String.format("(%d,%d)", h, v), p.toString());

        // next

        String nextErr = "A Position.next nem a jo iranyban levo szomszedos pozictiot ad vissza - ";
        Direction dir = Direction.UP_LEFT;
        Position q = p.next(dir);
        assertEquals(nextErr + dir, h - 1, q.h);
        assertEquals(nextErr + dir, v + 1, q.v);
        dir = Direction.UP_RIGHT;
        q = p.next(dir);
        assertEquals(nextErr + dir, h + 1, q.h);
        assertEquals(nextErr + dir, v + 1, q.v);
        dir = Direction.DOWN_LEFT;
        q = p.next(dir);
        assertEquals(nextErr + dir, h - 1, q.h);
        assertEquals(nextErr + dir, v - 1, q.v);
        dir = Direction.DOWN_RIGHT;
        q = p.next(dir);
        assertEquals(nextErr + dir, h + 1, q.h);
        assertEquals(nextErr + dir, v - 1, q.v);

        // equals

        q = new Position(94, 42);
        p = new Position(94, 42);
        assertEquals("Position.equals hamisat adott vissza azonos poziciokra", p, q);
        q = new Position(2, 42);
        assertNotEquals("Position.equals igazat adott vissza kulonbozo poziciokra", p, q);
        assertNotEquals("Position.equals igazat adott vissza String parameter eseten", p, "almafa");

        // distance

        p = new Position(10, 16);
        q = new Position(18, 9);
        List<Integer> distance = new LinkedList<>();
        for (int n : p.distance(q))
            distance.add(n);
        assertEquals("Position.distance nem jol szamolja a tavolsagot", Arrays.asList(8, -7), distance);

        // isDiagonal

        String diagErr = "Position.isDiagonal szerint nem egy atlon helyezkednek el: ";
        String notDiagErr = "Position.isDiagonal szerint egy atlon helyezkednek el: ";        
        p = new Position(1,1);
        q = new Position(0,0);
        assertTrue(diagErr + p + " " + q, Position.isDiagonal(p,q));
        q = new Position(2,2);
        assertTrue(diagErr + p + " " + q, Position.isDiagonal(p,q));
        q = new Position(2,0);
        assertTrue(diagErr + p + " " + q, Position.isDiagonal(p,q));
        q = new Position(0,2);
        assertTrue(diagErr + p + " " + q, Position.isDiagonal(p,q));
        q = new Position(1,2);
        assertFalse(notDiagErr + p + " " + q, Position.isDiagonal(p,q));
        q = new Position(2,1);
        assertFalse(notDiagErr + p + " " + q, Position.isDiagonal(p,q));

        // hashCode
        h = 21; v = 10;
        p = new Position(h, v);
        assertEquals("Postion.hashCode nem a megadott keplet szerint szamol", 100 * v + h, p.hashCode());
    }

    static void denseGridTest() {
        // konstruktor, isValid

        Grid<Integer> grid = new DenseGrid<>(2,3);        
        Position valid = new Position(2,1);
        assertTrue("Az AbstractGrid.isValid hamis erteket adott helyes pozicio eseten: " + valid, grid.isValid(valid));
        Position invalid = new Position(1,2);
        assertFalse("Az AbstractGrid.isValid igaz erteket adott ervenytelen pozicio eseten: " + invalid, grid.isValid(invalid));
        invalid = new Position(3,1);
        assertFalse("Az AbstractGrid.isValid igaz erteket adott ervenytelen pozicio eseten: " + invalid, grid.isValid(invalid));
        invalid = new Position(0,-1);
        assertFalse("Az AbstractGrid.isValid igaz erteket adott ervenytelen pozicio eseten: " + invalid, grid.isValid(invalid));
        invalid = new Position(-1,0);
        assertFalse("Az AbstractGrid.isValid igaz erteket adott ervenytelen pozicio eseten:" + invalid, grid.isValid(invalid));

        // konstruktor, get

        assertTrue("A DenseGrid.get nem null-t ad vissza kezdetben", grid.get(valid) == null);

        try {
            grid.get(invalid);
            assertTrue("A DenseGrid.get nem dobott IndexOutOfBoundsException kivetelt ervenytelen index eseten", false);
        } catch (IndexOutOfBoundsException e) {
        } catch (Throwable e) {
            assertTrue("A DenseGrid.get nem IndexOutOfBoundsException kivetelt dobott ervenytelen index eseten", false);
        }

        // set

        try {
            grid.set(invalid, 100);
            assertTrue("A DenseGrid.set nem dobott IndexOutOfBoundsException kivetelt ervenytelen index eseten", false);
        } catch (IndexOutOfBoundsException e) {
        } catch (Throwable e) {
            assertTrue("A DenseGrid.set nem IndexOutOfBoundsException kivetelt dobott ervenytelen index eseten", false);
        }

        // set, get

        grid.set(valid, 25);
        assertEquals("A DenseGrid.set nem jo poziciora teszi az uj elemet, vagy a DenseGrid.get nem jo helyrol adja vissza", 25, grid.get(valid));
    }

    

    static void sparseGridTest() {
        Grid<Integer> grid = new SparseGrid<>(2,3);
        Position valid = new Position(2,1);
        Position invalid = new Position(1,3);

        // konstruktor, get

        assertTrue("A DenseGrid.get nem null-t ad vissza kezdetben", grid.get(valid) == null);

        try {
            grid.get(invalid);
            assertTrue("A DenseGrid.get nem dobott IndexOutOfBoundsException kivetelt ervenytelen index eseten", false);
        } catch (IndexOutOfBoundsException e) {
        } catch (Throwable e) {
            assertTrue("A DenseGrid.get nem IndexOutOfBoundsException kivetelt dobott ervenytelen index eseten", false);
        }

        // set

        try {
            grid.set(invalid, 100);
            assertTrue("A DenseGrid.set nem dobott IndexOutOfBoundsException kivetelt ervenytelen index eseten", false);
        } catch (IndexOutOfBoundsException e) {
        } catch (Throwable e) {
            assertTrue("A DenseGrid.set nem IndexOutOfBoundsException kivetelt dobott ervenytelen index eseten", false);
        }

        // set, get

        grid.set(valid, 25);
        assertEquals("A DenseGrid.set nem jo poziciora teszi az uj elemet, vagy a DenseGrid.get nem jo helyrol adja vissza", 25, grid.get(valid));      
    }

    static void manTest() {
        // egyszerű lepesek

        Grid<Piece> grid = new SparseGrid<Piece>(5,5);
        Position center = new Position(2,2);
        Position center_clone = new Position(2,2);
        Position notDiag = new Position(2,1);
        Position oneStep = new Position(1,1);
        Position oneOneStep = new Position(0,0);
        Position twoStep = new Position(4,0);

        Man m = new Man(center, Piece.Color.BLACK, grid);

        String regularErr = "A Man.canStepTo hamisat adott egy szabalyos lepes eseten: ";
        assertTrue(regularErr + center + " -> " + oneStep, m.canStepTo(oneStep));

        String irregularErr = "A Man.canStepTo igazat adott egy szabalytalan lepes eseten: ";
        assertFalse(irregularErr + "ket lepes tavolsag " + center + " -> " + twoStep, m.canStepTo(twoStep));
        assertFalse(irregularErr + "nem atlos lepes " + center + " -> " + notDiag, m.canStepTo(notDiag));
        assertFalse(irregularErr + "nem is akarunk lepni " + center + " -> " + center_clone, m.canStepTo(center_clone));

        try {
            m.stepTo(oneStep);
        } catch (Throwable e) {
            assertTrue("A Man.stepTo valamilyen kivetelt dobott szabalyos lepes eseten", false);
        }

        assertTrue(regularErr + "meg egyet lepunk " + oneStep + " -> " + oneOneStep, m.canStepTo(oneOneStep));

        Position side = new Position(0,2);
        Position offBoard = side.next(Direction.UP_LEFT);
        Man m0 = new Man(side, Piece.Color.BLACK, grid);
        assertFalse(regularErr + side + " -> " + offBoard, m0.canStepTo(offBoard));

        // utes

        grid = new DenseGrid<>(5,5);
        Position p1 = new Position(2,2);
        Position p2 = new Position(1,1);
        Position p3 = new Position(3,1);
        Position p4 = new Position(3,3);
        Position p5 = new Position(4,4);
        Man m1 = new Man(p1, Piece.Color.BLACK, grid);
        Man enemy = new Man(p2, Piece.Color.WHITE, grid);
        Man m2 = new Man(p3, Piece.Color.BLACK, grid);
        Man enemy2 = new Man(p4, Piece.Color.WHITE, grid);
        Man m3 = new Man(p5, Piece.Color.BLACK, grid);
        grid.set(p1, m1);
        grid.set(p2, enemy);
        grid.set(p3, m2);
        grid.set(p4, enemy2);
        grid.set(p5, m3);

        Position jump = new Position(0,0);
        Position noJump = new Position(4,0); 
        Position wrongDirection = new Position(1,3);
        Position notEmpty = new Position(3,1);

        assertTrue("A Man.canStepTo hamisat adott egy szabalyos utes eseten", m1.canStepTo(jump));
        assertFalse("A Man.canStepTo igazat adott egy szabalytalan utes eseten: sajat babu atugrasa", m1.canStepTo(noJump));
        assertFalse("A Man.canStepTo igazat adott, ha egy nem ures mezore lepnenk", m1.canStepTo(notEmpty));
        assertFalse("A Man.canStepTo igazat adott, ha rossz iranyba lepnenk", m1.canStepTo(wrongDirection));
        assertFalse("A Man.canStepTo igazat adott, ha utnenk, de a cel mezo nem ures", m3.canStepTo(p1));

        try {
            m1.stepTo(wrongDirection);
            assertTrue("A Man.stepTo nem dobott InvalidStepException kivetelt szabalytalan utes eseten", false);
        } catch (InvalidStepException e) {
        } catch (Throwable e) {
            assertTrue("A Man.stepTo nem InvalidStepException kivetelt dobott szabalytalan utes eseten", false);
        }

        try {
            m1.stepTo(jump);
            assertTrue("A Man.stepTo nem vette le az ellenfel leutott babujat a tablarol", grid.get(p2) == null);
            assertTrue("A Man.stepTo nem tette uresse a babu eredeti helyet a tablan", grid.get(p1) == null);
            assertTrue("A Man.stepTo nem tette at a babut az új helyre a tablan", grid.get(jump) == m1);
        } catch (InvalidStepException e) {
            assertTrue("A Man.stepTo InvalidStepException kivetelt dobott szabalyos utes eseten", false);
        }

        assertEquals("A Man.toString nem jo szoveget adott vissza", "BM", m1.toString());
    }

    static void kingTest() {
        // egyszerű lepesek

        Grid<Piece> grid = new SparseGrid<Piece>(5,5);
        Position center = new Position(2,2);
        Position center_clone = new Position(2,2);
        Position notDiag = new Position(2,1);
        Position oneStep = new Position(1,3);
        Position twoStep = new Position(0,4);

        King m = new King(center, Piece.Color.WHITE, grid);

        String regularErr = "A King.canStepTo hamisat adott egy szabalyos lepes eseten: ";
        assertTrue(regularErr + center + " -> " + oneStep, m.canStepTo(oneStep));

        String irregularErr = "A King.canStepTo igazat adott egy szabalytalan lepes eseten: ";
        assertFalse(irregularErr + "ket lepes tavolsag " + center + " -> " + twoStep, m.canStepTo(twoStep));
        assertFalse(irregularErr + "nem atlos lepes " + center + " -> " + notDiag, m.canStepTo(notDiag));
        assertFalse(irregularErr + "nem is akarunk lepni " + center + " -> " + center_clone, m.canStepTo(center_clone));


        Position side = new Position(0,2);
        Position offBoard = side.next(Direction.UP_LEFT);
        King m0 = new King(side, Piece.Color.WHITE, grid);
        assertFalse(irregularErr + side + " -> " + offBoard, m0.canStepTo(offBoard));

        // utes

        grid = new DenseGrid<>(5,5);
        Position p1 = new Position(2,2);
        Position p2 = new Position(1,1);
        Position p3 = new Position(3,1);
        Position p4 = new Position(3,3);
        Position p5 = new Position(4,4);
        King m1 = new King(p1, Piece.Color.BLACK, grid);
        King enemy = new King(p2, Piece.Color.WHITE, grid);
        King m2 = new King(p3, Piece.Color.BLACK, grid);
        King enemy2 = new King(p4, Piece.Color.WHITE, grid);
        King m3 = new King(p5, Piece.Color.BLACK, grid);
        grid.set(p1, m1);
        grid.set(p2, enemy);
        grid.set(p3, m2);     
        grid.set(p4, enemy2);
        grid.set(p5, m3);  

        Position jump = new Position(0,0);
        Position noJump = new Position(4,0); 
        Position otherDirection = new Position(1,3);
        Position notEmpty = new Position(3,1);

        assertTrue("A King.canStepTo hamisat adott egy szabalyos utes eseten", m1.canStepTo(jump));
        assertFalse("A King.canStepTo igazat adott egy szabalytalan utes eseten: sajat babu atugrasa", m1.canStepTo(noJump));
        assertFalse("A King.canStepTo igazat adott, ha egy nem ures mezore lepnenk", m1.canStepTo(notEmpty));
        assertTrue("A King.canStepTo hamisat adott szabalyos lepes eseten", m1.canStepTo(otherDirection));
        assertFalse("A King.canStepTo igazat adott, ha utnenk, de a cel mezo nem ures", m3.canStepTo(p1));

        assertEquals("A King.toString nem jo szoveget adott vissza", "BK", m1.toString());

    }

    static void assertTrue(String msg, Boolean b) {
        if (!b) {
            System.err.println("HIBA: " + msg);
            System.exit(1);
        }
    }

    static void assertFalse(String msg, Boolean b) {
        if (b) {
            System.err.println("HIBA: " + msg);
            System.exit(1);
        }
    }

    static void assertEquals(String msg, Object a, Object b) {
        if (!a.equals(b)) {
            System.err.println("HIBA: " + msg + ": '" + String.valueOf(a) + "' /= '" + String.valueOf(b) + "'");
            System.exit(1);
        }
    }

    static void assertNotEquals(String msg, Object a, Object b) {
        if (a.equals(b)) {
            System.err.println("HIBA: " + msg + ": '" + a.toString() + "' == '" + b.toString() + "'");
            System.exit(1);
        }
    }

}
