import { useEffect, useState } from "react";
import "./Dashboard.css";

function Dashboard() {

    const [stats, setStats] = useState(null);

    useEffect(() => {

        fetch("http://localhost:8089/api/dashboard/stats")
            .then(res => res.json())
            .then(data => setStats(data));

    }, []);

    if (!stats) {

        return <h2>Loading Dashboard...</h2>;
    }

    return (

        <div className="dashboard">

            <h1>AI Analytics Dashboard</h1>

            <div className="cards">

                <div className="card">
                    <h2>Total Requests</h2>
                    <p>{stats.totalRequests}</p>
                </div>

                <div className="card">
                    <h2>Success</h2>
                    <p>{stats.successRequests}</p>
                </div>

                <div className="card">
                    <h2>Failed</h2>
                    <p>{stats.failedRequests}</p>
                </div>

                <div className="card">
                    <h2>Avg Latency</h2>
                    <p>
                        {stats.averageLatency.toFixed(2)} ms
                    </p>
                </div>

            </div>

            <h2>Inference Logs</h2>

            <table>

                <thead>

                <tr>

                    <th>ID</th>
                    <th>Provider</th>
                    <th>Model</th>
                    <th>Latency</th>
                    <th>Status</th>
                    <th>Session</th>

                </tr>

                </thead>

                <tbody>

                {stats.logs.map(log => (

                    <tr key={log.id}>

                        <td>{log.id}</td>

                        <td>{log.provider}</td>

                        <td>{log.modelName}</td>

                        <td>{log.latencyMs} ms</td>

                        <td>{log.status}</td>

                        <td>{log.sessionId}</td>

                    </tr>

                ))}

                </tbody>

            </table>

        </div>
    );
}

export default Dashboard;