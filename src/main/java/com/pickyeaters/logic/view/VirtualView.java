<<<<<<<< HEAD:src/main/java/com/pickyeaters/logic/view/VirtualView.java
package com.pickyeaters.app.view;
========
package com.pickyeaters.logic.view.cli;
>>>>>>>> c398112c961a9ce692bc4119a6c35de2abed95ef:src/main/java/com/pickyeaters/logic/view/cli/VirtualView.java

import com.pickyeaters.logic.controller.application.MainController;

public abstract class VirtualView {
    protected MainController controller;
    public VirtualView(MainController controller) {
        this.controller = controller;
    }

    public abstract void show();
}
