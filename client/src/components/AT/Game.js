import React from "react";
import at from "../../api/at";

class Game extends React.Component {

    constructor(props) {
        super(props);
        this.state = {team1: 'Choose...', team2: 'Choose...'};
    }


    onChangeTeam1 = (event) => {
        this.setState({team1: event.target.value});
    }

    onChangeTeam2 = (event) => {
        this.setState({team2: event.target.value});
    }

    getResult = () => {
        if (this.state.team1 !== 'Choose...' && this.state.team2 !== 'Choose...' && this.state.team1 !== this.state.team2) {
            at.get('/games/result/' + this.state.team1 + '/' + this.state.team2).then(r => console.log('OK'));
        } else {
            alert("BAD FORM PARAMS!");
        }
    }

    render() {
        return (
            <div>
                <div className={`d-flex justify-content-center font-weight-bold `}>
                    <h1 className={`text-dark m-5`}>Match prediction</h1>
                </div>
                <div className={`row`}>
                    <div className={`col-6`}>
                        <label htmlFor="team1">Team 1:</label>
                        <select className="form-control" id="team1" value={this.state.team1} onChange={this.onChangeTeam1}>
                            <option value={`Choose...`}>Choose...</option>
                            <option value={`Brooklyn`}>Brooklyn</option>
                            <option value={`Oklahoma`}>Oklahoma</option>
                            <option value={`Juta`}>Juta</option>
                            <option value={`GSW`}>GSW</option>
                            <option value={`Philadelphia`}>Philadelphia</option>
                        </select>
                    </div>
                    <div className={`col-6`}>
                        <label htmlFor="team1">Team 2:</label>
                        <select className="form-control" id="team1" value={this.state.team2} onChange={this.onChangeTeam2}>
                            <option value={`Choose...`}>Choose...</option>
                            <option value={`Brooklyn`}>Brooklyn</option>
                            <option value={`Oklahoma`}>Oklahoma</option>
                            <option value={`Juta`}>Juta</option>
                            <option value={`GSW`}>GSW</option>
                            <option value={`Philadelphia`}>Philadelphia</option>
                        </select>
                    </div>
                </div>

                <div className={`d-flex justify-content-center`}>
                    <button className={`btn btn-success w-50 mt-3`} onClick={this.getResult}>Get result</button>
                </div>
            </div>


        );
    }
}

export default Game;

