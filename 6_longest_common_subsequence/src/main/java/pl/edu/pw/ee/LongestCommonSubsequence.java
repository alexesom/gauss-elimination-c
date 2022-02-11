package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class LongestCommonSubsequence {
    String firstStr;
    String secondStr;
    CounterWithDirection[][] counterArray;
    int counterRowSize;
    int counterColumnSize;
    IntegerListMap mapWithLCSWayCoordinates = new IntegerListMap();

    public LongestCommonSubsequence(String firstStr, String secondStr) {
        if (firstStr == null || secondStr == null)
            throw new NullPointerException();

        this.firstStr = firstStr;
        this.secondStr = secondStr;
        this.counterColumnSize = firstStr.length() + 1;
        this.counterRowSize = secondStr.length() + 1;

        this.counterArray = new CounterWithDirection[firstStr.length() + 1][secondStr.length() + 1];

        for (int i = 0; i < counterColumnSize; i++)
            for (int j = 0; j < counterRowSize; j++)
                counterArray[i][j] = new CounterWithDirection();

        for (int i = 0; i < counterColumnSize; i++)
            counterArray[i][0].counter = 0;
        for (int i = 0; i < counterRowSize; i++)
            counterArray[0][i].counter = 0;
    }

    public String findLCS() {
        fillCounterArray(counterArray, firstStr, secondStr);
        int i = counterColumnSize - 1;
        int j = counterRowSize - 1;
        StringBuilder stringBuilder = new StringBuilder();

        while (counterArray[i][j].direction != null) {
            if (counterArray[i][j].direction == Direction.DIAGONAL && firstStr.charAt(i - 1) == secondStr.charAt(j - 1)) {
                stringBuilder.append(firstStr.charAt(i - 1));
                mapWithLCSWayCoordinates.addPair(i, j);
                i--;
                j--;
            } else if (counterArray[i][j].direction == Direction.UP) {
                mapWithLCSWayCoordinates.addPair(i, j);
                i--;
            } else if (counterArray[i][j].direction == Direction.LEFT) {
                mapWithLCSWayCoordinates.addPair(i, j);
                j--;
            }
        }

        mapWithLCSWayCoordinates.addPair(i, j);

        String resultString = stringBuilder.toString();
        StringBuilder result = new StringBuilder();

        for (i = 0; i < resultString.length(); i++) {
            result.insert(0, resultString.charAt(i));
        }

        makeCounterArrayDirectionsNullExceptWay();

        return result.toString();
    }

    public void display() {
        fillCounterArray(counterArray, firstStr, secondStr);
        findLCS();
        makeCounterArrayDirectionsNullExceptWay();

        StringBuilder modifiedFirstString = new StringBuilder();
        modifiedFirstString.append(" ").append(firstStr);

        StringBuilder modifiedSecondString = new StringBuilder();
        modifiedSecondString.append(" ").append(" ").append(secondStr);

        StringBuilder resultTable = new StringBuilder();
        StringBuilder lineWithPluses = new StringBuilder();

        lineWithPluses.append("+------")
                .append("-+----".repeat(Math.max(0, modifiedSecondString.length() - 1))).append('\n');


        resultTable.append(writeLegendRow(modifiedSecondString));

        for (int rowNumber = 0; rowNumber < modifiedFirstString.length(); rowNumber++) {
            StringBuilder firstInnerRow = new StringBuilder();
            StringBuilder secondInnerRow = new StringBuilder();
            StringBuilder thirdInnerRow = new StringBuilder();

            if (modifiedFirstString.charAt(rowNumber) == '\n') {
                firstInnerRow.append("|       ");
                secondInnerRow.append("|   \\n  ");
                thirdInnerRow.append("|       ");
            } else if (modifiedFirstString.charAt(rowNumber) == '\r') {
                firstInnerRow.append("|       ");
                secondInnerRow.append("|   \\r  ");
                thirdInnerRow.append("|       ");
            } else if (modifiedFirstString.charAt(rowNumber) == '\t') {
                firstInnerRow.append("|       ");
                secondInnerRow.append("|   \\t  ");
                thirdInnerRow.append("|       ");
            } else {
                firstInnerRow.append("|       ");
                secondInnerRow.append(String.format("|   %s   ", modifiedFirstString.charAt(rowNumber)));
                thirdInnerRow.append("|       ");
            }

            for (int columnNumber = 0; columnNumber < modifiedSecondString.length() - 1; columnNumber++) {
                if (counterArray[rowNumber][columnNumber].direction == null) {
                    firstInnerRow.append("|     ");
                    secondInnerRow.append(String.format("|  %s  ", counterArray[rowNumber][columnNumber].counter));
                    thirdInnerRow.append("|     ");
                }
                if (counterArray[rowNumber][columnNumber].direction == Direction.UP) {
                    firstInnerRow.append("|  ^  ");
                    secondInnerRow.append(String.format("|  %s  ", counterArray[rowNumber][columnNumber].counter));
                    thirdInnerRow.append("|     ");
                }
                if (counterArray[rowNumber][columnNumber].direction == Direction.LEFT) {
                    firstInnerRow.append("|     ");
                    secondInnerRow.append(String.format("|< %s  ", counterArray[rowNumber][columnNumber].counter));
                    thirdInnerRow.append("|     ");
                }
                if (counterArray[rowNumber][columnNumber].direction == Direction.DIAGONAL) {
                    firstInnerRow.append("|\\    ");
                    secondInnerRow.append(String.format("|  %s  ", counterArray[rowNumber][columnNumber].counter));
                    thirdInnerRow.append("|     ");
                }
            }
            resultTable.append(firstInnerRow).append('\n')
                    .append(secondInnerRow).append('\n')
                    .append(thirdInnerRow).append('\n');

            resultTable.append(lineWithPluses);
        }


        System.out.println(resultTable);

    }

    private StringBuilder writeLegendRow(StringBuilder legendString) {
        StringBuilder legendRow = new StringBuilder();
        StringBuilder lineWithPluses = new StringBuilder();

        lineWithPluses.append("+------")
                .append("-+----".repeat(Math.max(0, legendString.length() - 1))).append('\n');


        legendRow.append(lineWithPluses);

        for (int innerIndex = 0; innerIndex < 3; innerIndex++) {

            if (innerIndex % 3 != 1)
                legendRow.append("|       ")
                        .append("|     ".repeat(Math.max(0, legendString.length() - 1))).append('\n');
            else {
                legendRow.append(String.format("|   %s   ", legendString.charAt(0)));

                for (int i = 1; i < legendString.length(); i++) {
                    if (legendString.charAt(i) == '\n') {
                        legendRow.append("|  \\n  ");
                    } else if (legendString.charAt(i) == '\t') {
                        legendRow.append("|  \\t  ");
                    } else if (legendString.charAt(i) == '\r') {
                        legendRow.append("|  \\r  ");
                    } else {
                        legendRow.append(String.format("|  %s  ", legendString.charAt(i)));
                        legendRow.append("|  \\n  ");
                    }
                }

                legendRow.append('\n');
            }
        }

        legendRow.append(lineWithPluses);

        return legendRow;
    }

    private void fillCounterArray(CounterWithDirection[][] counterArray, String firstString, String secondString) {
        for (int i = 1; i < counterColumnSize; i++) {
            for (int j = 1; j < counterRowSize; j++) {
                if (firstStr.charAt(i - 1) == secondStr.charAt(j - 1)) {
                    counterArray[i][j].counter = counterArray[i - 1][j - 1].counter + 1;
                    counterArray[i][j].direction = Direction.DIAGONAL;
                } else {
                    if (counterArray[i - 1][j].counter >= counterArray[i][j - 1].counter) {
                        counterArray[i][j].counter = counterArray[i - 1][j].counter;
                        counterArray[i][j].direction = Direction.UP;
                    } else {
                        counterArray[i][j].counter = counterArray[i][j - 1].counter;
                        counterArray[i][j].direction = Direction.LEFT;
                    }
                }
            }
        }
    }

    private void makeCounterArrayDirectionsNullExceptWay() {
        for (int i = 0; i < counterColumnSize; i++) {
            for (int j = 0; j < counterRowSize; j++) {
                if (!mapWithLCSWayCoordinates.isInMap(i, j)) {
                    counterArray[i][j].direction = null;
                }
            }
        }
    }

    private static class IntegerListMap {
        List<Integer> keyMapList;
        List<Integer> valueMapList;

        IntegerListMap(List<Integer> keyMapList, List<Integer> valueMapList) {
            this.keyMapList = keyMapList;
            this.valueMapList = valueMapList;
        }

        IntegerListMap() {
            this.keyMapList = new ArrayList<>();
            this.valueMapList = new ArrayList<>();
        }

        public void addPair(Integer key, Integer value) {
            keyMapList.add(key);
            valueMapList.add(value);
        }

        public boolean isInMap(Integer key, Integer value) {
            int index = 0;
            for (Integer element : keyMapList) {
                if (element.equals(key)) {
                    if (valueMapList.get(index).equals(value))
                        return true;
                }
                index++;
            }
            return false;
        }
    }

    private static class CounterWithDirection {
        int counter;
        Direction direction;

        CounterWithDirection(int counter, Direction direction) {
            this.counter = counter;
            this.direction = direction;
        }

        CounterWithDirection() {
            this.counter = 0;
            this.direction = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CounterWithDirection)) return false;
            CounterWithDirection that = (CounterWithDirection) o;
            return Objects.equals(counter, that.counter) && direction == that.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(counter, direction);
        }
    }
}
