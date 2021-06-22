import React from "react";
import {ACLMessage} from "../../model/ACLMessage";
import at from "../../api/at";
import handshake from "../../api/handshake";

class Message extends React.Component {

    message = new ACLMessage();

    constructor(props) {
        super(props);
        this.state = {
            receiver: 'Choose...',
            aids: [],
            content: '',
            performative: 'Choose...'
        };
    }

    renderPerformatives = () => {
        return this.props.performatives.map((performative) => {
            return <option key={performative} value={performative}>{performative}</option>
        })
    }

    sendMessage = () => {
        if (this.state.performative !== 'Choose...') {
            this.message.receivers = this.state.aids;
            this.message.performative = this.state.performative;
            this.message.content = this.state.content;
            // at.post('agents/messages', this.message).then((response) => {
            //     alert("Success!");
            //     this.clearMessage();
            // })
            handshake.post('/messages', this.message).then(r => {
                alert("Success!");
                this.clearMessage();
            });

        } else {
            alert("Wrong performative!");
        }
    }


    onChangePerformative = (event) => {
        this.setState({performative: event.target.value});
    }

    onChangeSelectedAgent = (event) => {
        this.setState(({receiver: event.target.value}));
    }

    clearMessage = () => {
        this.props.clearMessage();
        this.setState({
            receiver: 'Choose...',
            aids: [],
            content: '',
            performative: 'Choose...'
        });
    }

    onChangeContent = (event) => {
        this.setState({content: event.target.value});
    }

    render() {
        return (
            <div>
                <h4 className={`text-dark font-weight-bold mb-3`}>Send message:</h4>
                    <div className="form-group">
                        <label htmlFor="performatives">Performative:</label>
                        <select className="form-control" id="performatives" value={this.state.performative} onChange={this.onChangePerformative}>
                            <option value={`Choose...`}>Choose...</option>
                            {this.renderPerformatives()}
                        </select>
                    </div>
                    <div className="form-group">
                        <label htmlFor="content">Content:</label>
                        <input type="text" className="form-control" id="content" value={this.state.content} onChange={this.onChangeContent}/>
                    </div>

                <div className="form-group">
                    <label htmlFor="receivers">Add receiver:</label>
                    <div className={`row`}>
                        <div className={`col-8`}>
                            <select className="form-control w-100" id="receivers" value={this.state.receiver} onChange={this.onChangeSelectedAgent}>
                                <option value={`Choose...`}>Choose...</option>
                                {this.renderCreatedAgents()}
                            </select>
                        </div>

                        <div className={`col-4`}>
                            <button className={`btn btn-success w-100`} onClick={this.addReceiver}>Add</button>
                        </div>
                    </div>

                </div>

                <div className={`form-group`}>
                    {this.renderReceivers()}
                </div>



                    {/*<div className="form-group">*/}
                    {/*    <label htmlFor="exampleFormControlTextarea1">Example textarea</label>*/}
                    {/*    <textarea className="form-control" id="exampleFormControlTextarea1" rows="3"/>*/}
                    {/*</div>*/}

                    <div className={`d-flex justify-content-center`}>
                        <button className={`btn btn-success w-25 mt-3 mr-3`} onClick={this.sendMessage}>Send</button>
                        <button className={`btn btn-success w-25 mt-3 ml-3`} onClick={this.clearMessage}>Clear</button>
                    </div>

            </div>





        );
    }

    addReceiver = () => {
        if (this.state.receiver !== 'Choose...') {
            this.setState({aids: [...this.state.aids, this.props.createdAgents[this.state.receiver]]});
        }
    }

    renderCreatedAgents = () => {
        return this.props.createdAgents.map((item, index) => {
            if (!this.state.aids.includes(item)) {
                return (<option key={index} value={index}>[{item.name}] - ({item.agentType.name})</option>);
            }
            return null;
        });
    }

    renderReceivers = () => {
        if (this.state.aids.length === 0) {
            return (<label key={`noAids`} htmlFor="noAids">No receivers</label>);
        } else {
            return this.state.aids.map((item, index) => {
               return (
                   <div key={index}>
                       <label key={item.name} >[{item.name}] - ({item.agentType.name})</label>
                   </div>
               );
            });
        }
    }
}

export default Message;
