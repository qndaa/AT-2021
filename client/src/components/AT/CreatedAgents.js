import React from "react";

class CreatedAgents extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedAgent: "Choose..."
        }
    }

    deleteAgent = () => {
        this.props.deletedAgent(this.state.selectedAgent);
        this.setState({selectedAgent: 'Choose...'});
    }

    changeSelectedAgent = (event) => {
        this.setState({selectedAgent: event.target.value});
    }

    renderCreatedAgents = () => {
        return this.props.createdAgents.map((item, index) => {
            return (<option key={index} value={index}>[{item.name}] - ({item.agentType.name})</option>);
        });
    }



    render() {
        if (this.props.createdAgents.length === 0) {
            return (
                <div>
                    <h4 className={`text-dark font-weight-bold mt-5 mb-3`}>No created agents!</h4>
                </div>
            );
        } else {
            return (
                <div>
                    <h4 className={`text-dark font-weight-bold mt-5 mb-3`}>Created agents:</h4>
                    <div>
                        <div className={`border rounded w-100`}>
                            <div className={`row m-2`}>
                                <div className={`col-10`}>
                                    <select className="form-control" id="performatives" value={this.state.selectedAgent} onChange={this.changeSelectedAgent}>
                                        <option value={`Choose...`}>Choose...</option>
                                        {this.renderCreatedAgents()}
                                    </select>
                                </div>
                                <div className={`col-2 d-flex justify-content-end`}>
                                    <button className={`btn btn-danger`} onClick={this.deleteAgent}>Delete</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        }
    }
}

export default CreatedAgents;
