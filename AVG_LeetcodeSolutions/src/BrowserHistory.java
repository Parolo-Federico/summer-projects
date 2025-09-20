import java.util.ArrayList;
import java.util.List;

class BrowserHistory {
    int currentPage;
    List<String> history;
    int last;
    public BrowserHistory(String homepage) {
        history = new ArrayList<>();
        history.add(homepage);
        currentPage = 0;
    }

    public void visit(String url) {
        if (currentPage == history.size() - 1) {
            history.add(url);
            currentPage++;
            return;
        }
        history = history.subList(0,currentPage + 1);
        history.add(++currentPage,url);
    }

    public String back(int steps) {
        if (currentPage - steps >= 0) {
            currentPage -= steps;
            return history.get(currentPage);
        }else {
            currentPage = 0;
            return history.get(currentPage);
        }

    }

    public String forward(int steps) {
        if (currentPage + steps < history.size()) {
            currentPage += steps;
            return history.get(currentPage);
        } else {
            currentPage = history.size() - 1;
            return history.get(currentPage);
        }
    }
}

/**
 * Your BrowserHistory object will be instantiated and called as such:
 * BrowserHistory obj = new BrowserHistory(homepage);
 * obj.visit(url);
 * String param_2 = obj.back(steps);
 * String param_3 = obj.forward(steps);
 */