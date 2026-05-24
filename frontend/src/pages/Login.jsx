import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../App.css";
import robot from "../assets/ai.png";

function Login() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");

    const [password, setPassword] = useState("");

    const loginUser = async () => {

        try {

            const response = await fetch(
                "http://localhost:8089/api/auth/login",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        email,
                        password
                    })
                }
            );

            if (!response.ok) {

                alert("Invalid credentials");
                return;
            }

            const data = await response.json();

            localStorage.setItem(
                "token",
                data.token
            );

            navigate("/chat");

        } catch (error) {

            alert("Login failed");
        }
    };

    return (

        <div className="auth-page">

            <div className="auth-left">

                <img
                    src={robot}
                    alt="AI Robot"
                    className="auth-image"
                />

            </div>

            <div className="auth-right">

                <div className="auth-card">

                    <h1>AI Assistant</h1>

                    <p>
                        Login and continue your AI conversations.
                    </p>

                    <input
                        type="email"
                        placeholder="Enter email"
                        value={email}
                        onChange={(e) =>
                            setEmail(e.target.value)
                        }
                    />

                    <input
                        type="password"
                        placeholder="Enter password"
                        value={password}
                        onChange={(e) =>
                            setPassword(e.target.value)
                        }
                    />

                    <button onClick={loginUser}>
                        Login
                    </button>

                    <span>
                        Don't have account?
                    </span>

                    <Link to="/register">
                        Register
                    </Link>

                    <div style={{ marginTop: "20px" }}>

                        <Link to="/dashboard">

                            <button
                                style={{
                                    width: "100%",
                                    background: "#111827"
                                }}
                            >
                                Open Dashboard
                            </button>

                        </Link>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default Login;