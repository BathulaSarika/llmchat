import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../App.css";
import robot from "../assets/ai.png";

function Register() {

    const navigate = useNavigate();

    const [name, setName] = useState("");

    const [email, setEmail] = useState("");

    const [password, setPassword] = useState("");

    const registerUser = async () => {

        try {

            const response = await fetch(
                "http://localhost:8089/api/auth/register",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        name,
                        email,
                        password
                    })
                }
            );

            const data = await response.text();

            alert(data);

            navigate("/");

        } catch (error) {

            alert("Registration failed");
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

                    <h1>Create Account</h1>

                    <p>
                        Register and start chatting with AI.
                    </p>

                    <input
                        type="text"
                        placeholder="Enter name"
                        value={name}
                        onChange={(e) =>
                            setName(e.target.value)
                        }
                    />

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

                    <button onClick={registerUser}>
                        Register
                    </button>

                    <span>
                        Already have account?
                    </span>

                    <Link to="/">
                        Login
                    </Link>

                </div>

            </div>

        </div>
    );
}

export default Register;