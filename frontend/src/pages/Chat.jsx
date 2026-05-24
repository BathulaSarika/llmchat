import { useEffect, useState } from "react";
import "../App.css";

function Chat() {

    const [message, setMessage] = useState("");

    const [messages, setMessages] = useState([]);

    const [chatHistory, setChatHistory] = useState([]);

    const [currentChatId, setCurrentChatId] = useState(null);

    // LOAD CHAT HISTORY
    useEffect(() => {

        const savedChats =
            JSON.parse(localStorage.getItem("chatHistory")) || [];

        setChatHistory(savedChats);

    }, []);

    // CREATE NEW CHAT
    const createNewChat = () => {

        setMessages([]);

        setCurrentChatId(null);
    };

    // LOAD OLD CHAT
    const loadChat = (chat) => {

        setMessages(chat.messages);

        setCurrentChatId(chat.id);
    };

    // DELETE CHAT
    const deleteChat = (id) => {

        const updatedChats =
            chatHistory.filter(chat => chat.id !== id);

        setChatHistory(updatedChats);

        localStorage.setItem(
            "chatHistory",
            JSON.stringify(updatedChats)
        );

        if (currentChatId === id) {

            setMessages([]);

            setCurrentChatId(null);
        }
    };

    // SAVE CHAT
    const saveChat = (finalMessages) => {

        let updatedChats = [...chatHistory];

        if (currentChatId) {

            updatedChats = updatedChats.map(chat => {

                if (chat.id === currentChatId) {

                    return {
                        ...chat,
                        messages: finalMessages
                    };
                }

                return chat;
            });

        } else {

            const newChat = {

                id: Date.now().toString(),

                title:
                    finalMessages[0]?.text?.slice(0, 25)
                    || "New Chat",

                messages: finalMessages
            };

            updatedChats.unshift(newChat);

            setCurrentChatId(newChat.id);
        }

        setChatHistory(updatedChats);

        localStorage.setItem(
            "chatHistory",
            JSON.stringify(updatedChats)
        );
    };

    // SEND MESSAGE
    const sendMessage = async () => {

        if (!message.trim()) return;

        const userMessage = {
            sender: "USER",
            text: message
        };

        const updatedMessages = [
            ...messages,
            userMessage
        ];

        setMessages(updatedMessages);

        const currentMessage = message;

        setMessage("");

        try {

            const sessionId =
                currentChatId || Date.now().toString();

            const response = await fetch(
                "http://localhost:8089/api/chat/send",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        sessionId: sessionId,
                        content: currentMessage
                    })
                }
            );

            if (!response.ok) {

                throw new Error("API Failed");
            }

            const data = await response.json();

            const aiMessage = {
                sender: "AI",
                text: data.content
            };

            const finalMessages = [
                ...updatedMessages,
                aiMessage
            ];

            setMessages(finalMessages);

            if (!currentChatId) {

                setCurrentChatId(sessionId);
            }

            saveChat(finalMessages);

        } catch (error) {

            console.log(error);

            alert("Failed to get AI response");
        }
    };

    return (

        <div className="chat-container">

            {/* SIDEBAR */}

            <div className="sidebar">

                <h2 className="logo">
                    AI Assistant
                </h2>

                <button
                    className="new-chat-btn"
                    onClick={createNewChat}
                >
                    + New Chat
                </button>

                <h3 className="history-title">
                    Conversations
                </h3>

                <div className="chat-history">

                    {chatHistory.length === 0 ? (

                        <p className="empty-history">
                            No chats yet
                        </p>

                    ) : (

                        chatHistory.map(chat => (

                            <div
                                key={chat.id}
                                className="chat-item"
                            >

                                <div
                                    className="chat-text"
                                    onClick={() =>
                                        loadChat(chat)
                                    }
                                >
                                    {chat.title}
                                </div>

                                <button
                                    className="delete-btn"
                                    onClick={() =>
                                        deleteChat(chat.id)
                                    }
                                >
                                    ×
                                </button>

                            </div>

                        ))
                    )}

                </div>

            </div>

            {/* MAIN */}

            <div className="chat-main">

                <div className="chat-header">
                    Smart AI Chat
                </div>

                <div className="chat-box">

                    {messages.length === 0 ? (

                        <div className="welcome-box">

                            <h2>
                                Welcome to AI Assistant
                            </h2>

                            <p>
                                Start chatting with AI...
                            </p>

                        </div>

                    ) : (

                        messages.map((msg, index) => (

                            <div
                                key={index}
                                className={
                                    msg.sender === "USER"
                                        ? "message user"
                                        : "message ai"
                                }
                            >

                                <div className="sender">
                                    {msg.sender}
                                </div>

                                <div className="text">
                                    {msg.text}
                                </div>

                            </div>

                        ))
                    )}

                </div>

                {/* INPUT */}

                <div className="input-area">

                    <input
                        type="text"
                        placeholder="Type your message..."
                        value={message}
                        onChange={(e) =>
                            setMessage(e.target.value)
                        }
                        onKeyDown={(e) => {

                            if (e.key === "Enter") {

                                sendMessage();
                            }
                        }}
                    />

                    <button onClick={sendMessage}>
                        Send
                    </button>

                </div>

            </div>

        </div>
    );
}

export default Chat;