package dev.zopad.chatserver;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import dev.zopad.chatserver.service.ChatService;
import dev.zopad.model.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Chat",
        shortName = "Vaadin Chat",
        description = "Simple reactive chat app.",
        enableInstallPrompt = false)
@CssImport("./styles/styles.css")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@Push
public class MainView extends VerticalLayout {

    private String username;
    private final ChatService chatService;

    public MainView(ChatService chatService) {
        this.chatService = chatService;
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("main-view");
        H1 header = new H1("Secret Refi Chat");
        header.getElement().getThemeList().add("dark");

        add(header);

        askUsername();
    }

    private void askUsername() {
        HorizontalLayout layout = new HorizontalLayout();
        TextField usernameField = new TextField();
        usernameField.setPlaceholder("Username: ");
        Button startButton = new Button("Enter chat");
        layout.add(usernameField, startButton);
        add(layout);
        startButton.addClickListener(click -> {
            username = usernameField.getValue();
            remove(layout);
            showChat();
        });
    }

    private void showChat() {
        MessageList messageList = new MessageList();
        chatService.subscribe(message -> getUI().ifPresent(ui -> ui.access(() -> messageList.add(formatMessage(message)))));
        add(messageList, createInputLayout());
        expand(messageList);
    }

    private Paragraph formatMessage(Message message) {
        return new Paragraph(getHourMinSec(message.getDateTime()) +
                " " + message.getSender() + ": " + message.getMessage());
    }

    private String getHourMinSec(Date date) {
        return "[" + new SimpleDateFormat("HH:mm:ss").format(date) + "]";
    }

    private Component createInputLayout() {
        HorizontalLayout layout = new HorizontalLayout();

        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClickShortcut(Key.ENTER);

        sendButton.addClickListener(click -> {
            chatService.sendMessage(new Message(username, messageField.getValue(), new Date()));
            messageField.clear();
            messageField.focus();
        });
        messageField.focus();

        layout.add(messageField, sendButton);
        layout.setWidth("100%");
        layout.expand(messageField);
        return layout;
    }

}
