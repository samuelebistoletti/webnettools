<script lang="ts">
    import { onMount } from "svelte";
    import { fade } from "svelte/transition";
    import AppFooter from "./AppFooter.svelte";
    import type { Configuration, ToolMD } from "./Configuration";
    import Navigation from "./Navigation.svelte";
    import ResultList from "./ResultList.svelte";
    import type { ResultTask } from "./ResultTask";
    import GenericTool from "./GenericTool.svelte";

    enum Status {
        LOADING,
        READY,
        ERROR,
    }

    let resultListComponent: ResultList;
    let config: Configuration = { toolMD: [] };
    let status: Status = Status.LOADING;
    let errorMsg: string = "";
    let currentTool: ToolMD;
    let payload: any;

    onMount(async () => {
        try {
            const response = await self.fetch("__URL__" + "config");
            if (response.ok && response.body !== null) {
                config = ((await response.json()) as unknown) as Configuration;
                if (config.toolMD.length > 0) {
                    modeChanged(config.toolMD[0].name);
                } else {
                    status = Status.ERROR;
                    errorMsg = "Can't load config. Number of tools is 0.";
                }

                status = Status.READY;
                if (
                    window.location.search &&
                    window.location.search.indexOf("config=") >= 0
                ) {
                    let index = window.location.search.indexOf("config=") + 7;
                    let data = JSON.parse(
                        decodeURIComponent(
                            window.location.search.substring(index)
                        )
                    );
                    modeChanged(data.tool, data.payload);
                }
            }
            if (!response.ok) {
                status = Status.ERROR;
                errorMsg =
                    "Can't load config.<br />Details:<br /><pre>" +
                    (await response.text()) +
                    "</pre>";
            }
        } catch (e) {
            status = Status.ERROR;
            errorMsg =
                "Can't load config.<br />Details:<br /><pre>" + e + "</pre>";
        }
    });

    function modeChanged(mode: string, presetPayload: any = null) {
        currentTool = config.toolMD.filter((tool) => tool.name == mode)[0];
        currentTool.presetPayload = presetPayload;
    }

    function addResult(event: CustomEvent<ResultTask>) {
        resultListComponent.addResult(event.detail);
    }

    function edit(result: ResultTask) {
        payload = { ...result.payload };
        modeChanged(result.mode, payload);
    }
</script>

<main role="main" class="container-fluid">
    <h1>
        <img alt="" src="/favicon.ico" height="55px" />
        Eolo Net Tools
    </h1>

    {#if status === Status.READY}
        <div transition:fade={{ delay: 300, duration: 300 }}>
            <p class="container-fluid">{@html config.introText}</p>
            <div class="container-fluid">
                <Navigation
                    tools={config.toolMD}
                    mode={currentTool.name}
                    on:modeChanged={(ev) => modeChanged(ev.detail)}
                />

                <GenericTool
                    tool={currentTool}
                    on:createResult={addResult}
                    p={payload}
                />
            </div>
            <div class="container-fluid" style="padding-top: 0.5em">
                <ResultList
                    bind:this={resultListComponent}
                    on:edit={(ev) => edit(ev.detail)}
                />
            </div>
        </div>
    {:else if status === Status.LOADING}
        <div transition:fade={{ delay: 0, duration: 300 }}>
            <div class="d-flex justify-content-center">
                <div
                    class="spinner-border"
                    style="width: 3rem; height: 3rem; margin: 3rem;"
                    role="status"
                >
                    <span class="sr-only">Loading...</span>
                </div>
            </div>
        </div>
    {:else}
        <div transition:fade={{ delay: 300, duration: 300 }}>
            <div
                class="alert alert-danger"
                style="margin-top: 2rem;"
                role="alert"
            >
                {@html errorMsg}
            </div>
        </div>
    {/if}
</main>

<AppFooter />

<style>
    h1 {
        margin-left: 0.5em;
        margin-top: 0.3em;
    }
</style>
