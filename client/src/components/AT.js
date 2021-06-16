import React from "react";
import at from "../api/at";
import AvailableClassAgents from "./AT/AvailableClassAgents";
import CreatedAgents from "./AT/CreatedAgents";
import Message from "./AT/Message";
import Console from "./AT/Console";

class AT extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            performatives: [],
            createdAgents: [],
            classes: []
        };
    }

    componentDidMount = async () => {
        await at.get('agents/messages').then((response) => {
            this.setState({performatives: response.data});
        });
        await at.get('agents/running').then((agents) => {
            this.setState({createdAgents: agents.data})
        })
        await at.get('agents/classes').then((classes) => {
            this.setState({classes: classes.data});
        });
    }

    deleteAgent = async (index) => {
        console.log(index);
        const name = this.state.createdAgents[index].name;
        const host = this.state.createdAgents[index].host;
        const type = this.state.createdAgents[index].agentType.name;
        const module = this.state.createdAgents[index].agentType.module;


        console.log(JSON.stringify(this.state.createdAgents[index]));
        const headers = {
            'Content-Type': 'application/json'
        };

        await at.delete('agents/running',
            { headers, data: {
                    name,
                    host,
                    'agentType': {
                        "name": type,
                        module
                    }
            }}).then(_ => {
                this.componentDidMount();
                alert("Agent deleted");
        });
    }

    createAgent = (type, name) => {
        at.put('agents/running/' + type + '/' + name ).then((response) => {
            if (response.data === '') {
                alert("Agent with name already exists!");
            } else {
                this.componentDidMount().then(r => alert("Agent added!"));
            }
        });
    }

    render() {
        return (
            <div>
                <div className={`d-flex justify-content-center font-weight-bold `}>
                    <h1 className={`text-dark m-5`}>AT-agents</h1>
                </div>
                <div className={`row`}>

                    <div className={`col-6`}>
                        <div>
                            <AvailableClassAgents createAgent={this.createAgent} classes={this.state.classes}/>
                        </div>
                        <div>
                            <CreatedAgents deletedAgent={this.deleteAgent} createdAgents={this.state.createdAgents}/>
                        </div>
                    </div>

                    <div className={`col-6`}>
                        <Message
                            performatives={this.state.performatives}
                            createdAgents={this.state.createdAgents}
                            clearMessage={this.componentDidMount}
                        />
                    </div>
                </div>
                <div className={`w-100`}>
                    <Console/>
                </div>
            </div>

        );
    }




}

export default AT;
