import React from "react";
import at from "../api/at";
import AvailableClassAgents from "./AT/AvailableClassAgents";
import CreatedAgents from "./AT/CreatedAgents";

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
                        <h4 className={`text-dark font-weight-bold mb-3`}>Send message:</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="performatives">Performative:</label>
                                <select className="form-control" id="performatives">
                                    <option value={`Choose...`}>Choose...</option>
                                    {this.renderPerformatives()}
                                </select>
                            </div>
                            <div className="form-group">
                                <label htmlFor="exampleFormControlInput1">Email address</label>
                                <input type="email" className="form-control" id="exampleFormControlInput1"
                                       placeholder="name@example.com"/>
                            </div>

                            <div className="form-group">
                                <label htmlFor="exampleFormControlSelect2">Example multiple select</label>
                                <select multiple className="form-control" id="exampleFormControlSelect2">
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                    <option>5</option>
                                </select>
                            </div>
                            <div className="form-group">
                                <label htmlFor="exampleFormControlTextarea1">Example textarea</label>
                                <textarea className="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                            </div>
                        </form>




                    </div>

                </div>
            </div>

        );
    }

    renderPerformatives = () => {
        return this.state.performatives.map((performative) => {

            return <option key={performative} value={performative}>{performative}</option>
        })
    }


}

export default AT;
