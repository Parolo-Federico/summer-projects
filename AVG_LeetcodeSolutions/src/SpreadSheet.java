import java.util.HashMap;

class Spreadsheet {
    HashMap<String,Integer> spreadSheet;
    int maxRow;
    public Spreadsheet(int rows) {
        spreadSheet = new HashMap<>();
        maxRow = rows - 1;
        for (int i = 65; i <= 90; i++) {
            spreadSheet.put("" + (char)i + 1,0);
        }
    }

    public void setCell(String cell, int value) {
        spreadSheet.put(cell,value);
    }

    public void resetCell(String cell) {
        spreadSheet.put(cell,0);
    }

    public int getValue(String formula) {
        formula = formula.substring(1);
        String[] cells = formula.split("\\+");
        int res = 0;
        for (String cell : cells) {
            char c = cell.charAt(0);
            if (c < 65 || c > 90) {
                res += Integer.parseInt(cell);
            }
            else {
                Integer a = spreadSheet.get(cell);
                res += a == null ? 0 : a;
            }
        }
        return res;
    }
}

/**
 * Your Spreadsheet object will be instantiated and called as such:
 * Spreadsheet obj = new Spreadsheet(rows);
 * obj.setCell(cell,value);
 * obj.resetCell(cell);
 * int param_3 = obj.getValue(formula);
 */