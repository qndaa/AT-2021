import React from "react";

class AvailableClassAgents extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedNewAgentType: 'Choose...',
            newAgentName: '',
        }
    }

    renderAvailableAgentClass = () => {
        return this.props.classes.map((c) => {
            return (
                <option key={c.ejbName} value={c.ejbName}>{c.ejbName}</option>
            );
        })
    }

    changeSelectedNewAgentType = (event) => {
        this.setState({selectedNewAgentType: event.target.value});
    }

    changeNewAgentName = (event) => {
        this.setState({newAgentName: event.target.value});
    }

    createAgent = async () => {
        if (this.state.newAgentName !== '' && this.state.selectedNewAgentType !== 'Choose...') {
            await this.props.createAgent(this.state.selectedNewAgentType, this.state.newAgentName)
            this.setState({
                selectedNewAgentType: 'Choose...',
                newAgentName: ''
            })
        } else {
            alert('Wrong argument!');
        }
    }

    render() {
        return (
            <div>
                <h4 className={`text-dark font-weight-bold mb-3`}>Available class agents:</h4>
                <div>
                    <div className={`border rounded w-100`}>
                        <div className={`row m-2`}>
                            <div className={`col-4`}>
                                <select className="form-control" id="performatives" value={this.state.selectedNewAgentType} onChange={this.changeSelectedNewAgentType}>
                                    <option value={`Choose...`}>Choose...</option>
                                    {this.renderAvailableAgentClass()}
                                </select>
                            </div>
                            <div className={`col-6`}>
                                <input className="form-control" type={`text`} value={this.state.newAgentName} onChange={this.changeNewAgentName}/>
                            </div>
                            <div className={`col-2 d-flex justify-content-end`}>
                                <button className={`btn btn-success `} onClick={this.createAgent}>Create</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>



        );
    }


}

export default AvailableClassAgents;
